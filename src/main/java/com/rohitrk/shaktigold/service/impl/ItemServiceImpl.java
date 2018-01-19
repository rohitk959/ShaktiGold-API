package com.rohitrk.shaktigold.service.impl;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.ItemProperty;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.service.ItemService;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.util.CommonUtil;
import com.rohitrk.shaktigold.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDAO itemDAO;
    @Autowired
    UserService userService;
    @Autowired
    CommonUtil commonUtil;

    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.sender}")
    private String sender;
    @Value("${sms.receiver}")
    private String receiver;
    @Value("${sms.url}")
    private String smsUrl;
    @Value("${app.work.dir}")
    private String appWorkDir;

    @Override
    public void addItem(String category, String subcategory, ItemModel item) {

        int itemId = itemDAO.getLatestItemId() + 1;
        String fileExtension = StringUtils.substring(item.getImgUrl(),
                StringUtils.indexOf(item.getImgUrl(), "/") + 1,
                StringUtils.indexOf(item.getImgUrl(), ";"));

        item.setItemName(item.getItemName() + "_" + itemId);
        String fileName = item.getItemName().concat(".").concat(fileExtension);

        if (commonUtil.writeImageToDisk(item.getImgUrl(), Constants.ITEMS_IMG_DIR_PHY.concat(fileName))) {
            item.setImgUrl(Constants.ITEMS_IMG_DIR + fileName);
            itemDAO.insertItem(category, subcategory, item);
            itemDAO.insertItemProperty(category, subcategory, item);
        } else {
            log.error("Error write image file to disk. Image - {}", item.getItemName());
            throw new ApplicationException(500, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Error write image file to disk.", null);
        }
    }

    @Override
    public List<ItemModel> getAllItemsUser(String category, String subcategory, int limit, int offset) {
        List<ItemModel> items = itemDAO.fetchAllItems(category, subcategory, limit, offset);

        for (ItemModel lItem : items) {
            ItemModel lItemDetail = getItemDetails(lItem.getItemId());
            ListIterator<ItemProperty> itemPropIterator = lItemDetail.getItemProperty().listIterator();

            while (itemPropIterator.hasNext()) {
                ItemProperty itemProp = itemPropIterator.next();
                if (!itemProp.getName().equalsIgnoreCase("weight")) {
                    itemPropIterator.remove();
                }
            }

            lItem.setItemProperty(lItemDetail.getItemProperty());
            lItem.setImgUrl(commonUtil.contructURL(lItem.getImgUrl()));
        }

        return items;
    }

    @Override
    public ItemModel getItemDetails(int itemId) {
        ItemModel lItem = itemDAO.getItemDetails(itemId);
        lItem.setImgUrl(commonUtil.contructURL(lItem.getImgUrl()));
        return lItem;
    }

    @Override
    public boolean hasMoreItems(String category, String subcategory, int limit, int offset) {
        return itemDAO.checkHasMoreItems(category, subcategory, limit, offset);
    }

    @Override
    public List<ItemModel> getAllItemsAdmin(String category, String subcategory) {
        List<ItemModel> items = itemDAO.getAllItemsAdmin(category, subcategory);

        for (ItemModel lItem : items) {
            ItemModel lItemDetail = getItemDetails(lItem.getItemId());
            ListIterator<ItemProperty> itemPropIterator = lItemDetail.getItemProperty().listIterator();

            while (itemPropIterator.hasNext()) {
                ItemProperty itemProp = itemPropIterator.next();
                if (!itemProp.getName().equalsIgnoreCase("weight")) {
                    itemPropIterator.remove();
                }
            }

            lItem.setItemProperty(lItemDetail.getItemProperty());
            lItem.setImgUrl(commonUtil.contructURL(lItem.getImgUrl()));
        }

        return items;
    }

    @Override
    public void deleteItem(String itemId) {
        itemDAO.deleteItem(itemId);
    }

    @Override
    public void enableDisableItem(String itemId, boolean enabled) {
        itemDAO.enableDisableItem(itemId, enabled);
    }

    @Override
    public boolean sendEstimateSms(ItemModel item) {
        boolean smsSent = false;
        try {
            String data = constructSmsData("email", item.getItemId());
            triggerSms(data);
            smsSent = true;
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
        }
        return smsSent;
    }

    private String constructSmsData(String email, int itemId) {
        StringBuilder data = new StringBuilder();

        data.append("username=" + username);
        data.append("&password=" + password);
        data.append("&message=" + getSmsContent(email, String.valueOf(itemId)));
        data.append("&sender=" + sender);
        data.append("&numbers=" + receiver);

        return data.toString();
    }

    private void triggerSms(String data) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(smsUrl).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
        conn.getOutputStream().write(data.getBytes("UTF-8"));
        final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        final StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            stringBuffer.append(line);
        }
        rd.close();

        System.out.println(stringBuffer.toString());
    }

    private String getSmsContent(String email, String itemId) {

        UserAccountModel userAccount = userService.getUserDetails(email);

        return CommonUtil.getSmsBody(
                StringUtils.join(userAccount.getFirstName(), Constants.BLANK, userAccount.getLastName()),
                userAccount.getUserDetailsModel().getMobileNumber(), itemId);
    }

    @Override
    public boolean sendEstimateDB(ItemModel item) {
        boolean notified = false;

        notified = itemDAO.insertNotification(item);

        return notified;
    }
}
