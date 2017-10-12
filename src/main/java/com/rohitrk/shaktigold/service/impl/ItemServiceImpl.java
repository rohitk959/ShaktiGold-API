package com.rohitrk.shaktigold.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.ItemProperty;
import com.rohitrk.shaktigold.model.OrderModel;
import com.rohitrk.shaktigold.model.SubCategoryModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.service.ItemService;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.util.CommonUtil;
import com.rohitrk.shaktigold.util.Constants;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDAO itemDAO;
	@Autowired
	UserService userService;

	@Value("${host.ipaddress}")
	private String hostName;
	@Value("${host.port}")
	private String port;
	@Value("${host.protocol}")
	private String protocol;
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
	public boolean insertCategory(CategoryModel category) {

		return itemDAO.insertCategory(category);
	}

	@Override
	public List<CategoryModel> getAllCategory() {
		List<CategoryModel> categories = itemDAO.getAllCategory();

		for (CategoryModel category : categories) {
			if (null != category.getImgUrl()) {
				category.setImgUrl(
						this.protocol + "://" + this.hostName + ":" + this.port + "/ShaktiGold" + category.getImgUrl());
			}
		}

		return categories;
	}

	@Override
	public boolean insertSubCategory(CategoryModel category) {
		boolean subcategoryAdded = false;
		
		String fileExtension = StringUtils.substring(category.getSubcategory().get(0).getImgUrl(),
				StringUtils.indexOf(category.getSubcategory().get(0).getImgUrl(), "/") + 1,
				StringUtils.indexOf(category.getSubcategory().get(0).getImgUrl(), ";"));
		String fileName = category.getSubcategory().get(0).getSubcategoryName().concat(".").concat(fileExtension);
		
		if(writeImageToDisk(category.getSubcategory().get(0).getImgUrl(), Constants.SUBCATEGORY_IMG_DIR_PHY.concat(fileName))) {
			category.getSubcategory().get(0).setImgUrl(Constants.SUBCATEGORY_IMG_DIR + fileName);
			if(itemDAO.insertSubCategory(category)) {
				if (itemDAO.insertSubCategoryProperty(category)) {
					subcategoryAdded = true;
				} else {
					// Failed to add subcategory properties to database
				}
			} else {
				// Failed to add subcategory to database
			}
		} else {
			// Failed to write image to disk
		}
		
		return subcategoryAdded;
	}

	@Override
	public boolean updateSubCategory(CategoryModel category) {
		boolean subcategoryAdded = false;
		boolean subcategoryPropsAdded = false;

		subcategoryAdded = itemDAO.insertSubCategory(category);

		if (subcategoryAdded) {
			subcategoryPropsAdded = itemDAO.insertSubCategoryProperty(category);
		}
		return subcategoryAdded && subcategoryPropsAdded;
	}

	@Override
	public CategoryModel getAllSubCategory(CategoryModel category) {
		CategoryModel categoryVO = itemDAO.getAllSubCategory(category.getCategoryName());

		for (SubCategoryModel subcategory : categoryVO.getSubcategory()) {
			if (null != subcategory.getImgUrl()) {
				subcategory.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port + "/ShaktiGold"
						+ subcategory.getImgUrl());
			}
		}

		return categoryVO;
	}

	@Override
	public boolean registerItem(ItemModel item) {
		boolean itemRegistered = false;
		
		int itemId = itemDAO.getLatestItemId();
		++itemId;
		String fileExtension = StringUtils.substring(item.getImgUrl(), StringUtils.indexOf(item.getImgUrl(), "/") + 1, StringUtils.indexOf(item.getImgUrl(), ";"));
		String itemName = Constants.ITEM_FILE_NAME + itemId;
		String itemFileName = itemName.concat(".").concat(fileExtension);
		
		if(writeImageToDisk(item.getImgUrl(), Constants.ITEMS_IMG_DIR_PHY.concat(itemFileName))) {
			item.setItemName(itemName);
			item.setImgUrl(Constants.ITEMS_IMG_DIR + itemFileName);
			
			if (itemDAO.registerItem(item)) {
				if(itemDAO.registerItemProperty(item)) {
					itemRegistered = true;
				} else {
					//Failed to register item properties in database
				}
			} else {
				//Failed to register item in database
			}
		} else {
			//Image writing failed
		}

		return itemRegistered;
	}
	
	public boolean writeImageToDisk(String encodedImage, String absoluteFileName) {
		boolean imageWrite = false;
		
		String base64Image = encodedImage.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			
			// write the image to a file
			File outputfile = new File( appWorkDir + absoluteFileName );
			ImageIO.write(image, StringUtils.substringAfterLast(absoluteFileName, "."), outputfile);
			imageWrite = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return imageWrite;
	}

	@Override
	public List<SubCategoryProperty> getItemTemplate(ItemModel item) {
		return itemDAO.getItemTemplate(item);
	}

	@Override
	public List<ItemModel> getAllItems(ItemModel item) {
		List<ItemModel> items = itemDAO.getAllItems(item);

		for (ItemModel lItem : items) {

			ItemModel lItemDetail = getItemDetails(lItem);
			ListIterator<ItemProperty> itemPropIterator = lItemDetail.getItemProperty().listIterator();

			while (itemPropIterator.hasNext()) {
				ItemProperty itemProp = itemPropIterator.next();
				if (!itemProp.getName().equalsIgnoreCase("weight")) {
					itemPropIterator.remove();
				}
			}

			lItem.setItemProperty(lItemDetail.getItemProperty());
			lItem.setImgUrl(
					this.protocol + "://" + this.hostName + ":" + this.port + "/ShaktiGold" + lItem.getImgUrl());
		}
		
		return items;
	}

	@Override
	public boolean hasMoreItems(ItemModel item) {
		return itemDAO.checkHasMoreItems(item);
	}

	@Override
	public ItemModel getItemDetails(ItemModel item) {
		ItemModel lItem = itemDAO.getItemDetails(item);
		lItem.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port + "/ShaktiGold" + lItem.getImgUrl());
		return lItem;
	}

	@Override
	public boolean putItemToCart(ItemModel item) {
		boolean itemAdded = false;
		if (!itemExistsInCart(item)) {
			itemAdded = itemDAO.insertItemToCart(item);
		}
		return itemAdded;
	}

	private boolean itemExistsInCart(ItemModel item) {
		return itemDAO.itemExistsInCart(item);
	}

	@Override
	public List<ItemModel> getItemsFromCart(ItemModel item) {
		List<ItemModel> cartItems = itemDAO.getItemsFromCart(item);

		for (ItemModel lItem : cartItems) {
			lItem.setImgUrl(
					this.protocol + "://" + this.hostName + ":" + this.port + "/ShaktiGold" + lItem.getImgUrl());
		}

		return cartItems;

	}

	@Override
	public boolean deleteItemFromCart(ItemModel item) {
		return itemDAO.deleteItemFromCart(item);
	}

	@Override
	public boolean updateItemQtyInCart(ItemModel item) {
		return itemDAO.updateItemQtyInCart(item);
	}

	@Override
	public boolean placeOrder(ItemModel item) {
		return itemDAO.placeOrder(item);
	}

	@Override
	public boolean updateOrder(OrderModel order) {
		return itemDAO.updateOrder(order);
	}

	@Override
	public List<ItemModel> getAllUserOrder(ItemModel order) {
		
		List<ItemModel> items = itemDAO.getAllUserOrder(order); 
		
		CommonUtil.prepareImageURL(items, this.protocol, this.hostName, this.port);
		
		return items;
	}

	@Override
	public boolean sendEstimateSms(ItemModel item) {
		boolean smsSent = false;
		try {
			String data = constructSmsData(item.getEmail(), item.getItemId());
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
	public List<ItemModel> getAllAdminOrder(ItemModel order) {
		List<ItemModel> orders = itemDAO.getAllAdminOrder(order); 
		
		CommonUtil.prepareImageURL(orders, this.protocol, this.hostName, this.port);
		
		return orders;
	}

	@Override
	public boolean updateOrderAdmin(ItemModel order) {
		return itemDAO.updateOrderAdmin(order);
	}

	@Override
	public UserAccountModel getUserProfileByInvoiceNumber(ItemModel order) {
		String email = userService.getEmailByInvoiceNumber(order.getInvoiceNumber());
		return userService.getUserDetails(email);
	}

	@Override
	public CategoryModel getAllSubCategoryForAdmin(CategoryModel category) {
		CategoryModel categoryVO = itemDAO.getAllSubCategoryForAdmin(category.getCategoryName());

		for (SubCategoryModel subcategory : categoryVO.getSubcategory()) {
			if (null != subcategory.getImgUrl()) {
				subcategory.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port + "/ShaktiGold"
						+ subcategory.getImgUrl());
			}
		}

		return categoryVO;
	}

	@Override
	public boolean enableDisableSubcategory(String subcategory, boolean hidden) {
		boolean result = false;
		
		result = itemDAO.enableDisableSubcategory(subcategory, hidden);
		
		return result;
	}

	@Override
	public List<ItemModel> getAllItemsAdmin(ItemModel item) {
		List<ItemModel> items = itemDAO.getAllItemsAdmin(item);

		for (ItemModel lItem : items) {

			ItemModel lItemDetail = getItemDetails(lItem);
			ListIterator<ItemProperty> itemPropIterator = lItemDetail.getItemProperty().listIterator();

			while (itemPropIterator.hasNext()) {
				ItemProperty itemProp = itemPropIterator.next();
				if (!itemProp.getName().equalsIgnoreCase("weight")) {
					itemPropIterator.remove();
				}
			}

			lItem.setItemProperty(lItemDetail.getItemProperty());
			lItem.setImgUrl(
					this.protocol + "://" + this.hostName + ":" + this.port + "/ShaktiGold" + lItem.getImgUrl());
		}
		
		return items;
	}

	@Override
	public boolean enableDisableItem(String itemId, boolean hidden) {
		return itemDAO.enableDisableItem(itemId, hidden);
	}

	@Override
	public boolean deleteSubcategory(String subcategory) {
		return itemDAO.deleteSubcategory(subcategory);
	}

	@Override
	public boolean deleteItem(String itemId) {
		return itemDAO.deleteItem(itemId);
	}

	@Override
	public boolean sendEstimateDB(ItemModel item) {
		boolean notified = false;
		
		notified = itemDAO.insertNotification(item);
		
		return notified;
	}
}
