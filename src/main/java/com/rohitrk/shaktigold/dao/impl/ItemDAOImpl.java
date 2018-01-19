package com.rohitrk.shaktigold.dao.impl;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.mapper.*;
import com.rohitrk.shaktigold.model.*;
import com.rohitrk.shaktigold.query.ItemQuery;
import com.rohitrk.shaktigold.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("itemDao")
public class ItemDAOImpl implements ItemDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertItem(String category, String subcategory, ItemModel item) {
        try {
            jdbcTemplate.update(ItemQuery.INSERT_ITEM,
                    new Object[]{item.getItemName(), item.getImgUrl(), subcategory, category});
        } catch (DataAccessException e) {
            log.error("Unable to store item. Error - {}", e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void insertItemProperty(String category, String subcategory, ItemModel item) {
        try {
            for (ItemProperty prop : item.getItemProperty()) {
                jdbcTemplate.update(ItemQuery.INSERT_ITEM_PROPERTY, new Object[]{prop.getValue(),
                        item.getItemName(), prop.getName(), subcategory, category});
            }
        } catch (DataAccessException e) {
            log.error("Unable to store item properties. Error - {}", e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<ItemModel> fetchAllItems(String category, String subcategory, int limit, int offset) {
        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_ITEMS, new Object[]{subcategory, category, limit, offset}, new ItemMapper());
        } catch (DataAccessException e) {
            log.error("Unable to fetch items from database. Error - {}", e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public ItemModel getItemDetails(int itemId) {
        try {
            return jdbcTemplate.queryForObject(ItemQuery.GET_ITEM_DETAILS, new Object[]{itemId}, new ItemDetailsMapper());
        } catch (DataAccessException e) {
            log.error("Unable to fetch item details from database. Error - {}", e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public boolean checkHasMoreItems(String category, String subcategory, int limit, int offset) {
        List<ItemModel> itemList = null;

        try {
            itemList = jdbcTemplate.query(ItemQuery.GET_ALL_ITEMS, new Object[]{subcategory, category, limit, offset + limit}, new ItemMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return itemList.size() > 0 ? true : false;
    }

    @Override
    public int getLatestItemId() {

        try {
            return jdbcTemplate.queryForObject(ItemQuery.GET_LATEST_ITEM_ID, Integer.class);
        } catch (DataAccessException | NullPointerException e) {
            //Fresh Item table inserting first item into table
            log.warn("First item into item table");
            return 0;
        }
    }

    @Override
    public CategoryModel getAllSubCategoryForAdmin(String categoryName) {
        CategoryModel subcategory = null;

       /* try {
            subcategory = jdbcTemplate.queryForObject(ItemQuery.GET_ALL_SUB_CATEGORY_FOR_ADMIN, new Object[]{categoryName},
                    new SubCategoryMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }*/

        return subcategory;
    }

    @Override
    public boolean enableDisableSubcategory(String subcategory, boolean hidden) {
        int recordsUpdated = 0;

        try {
            recordsUpdated = jdbcTemplate.update(ItemQuery.ENABLE_DISABLE_SUBCATEGORY, hidden == true ? 0 : 1, subcategory);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return recordsUpdated > 0 ? true : false;
    }

    @Override
    public List<ItemModel> getAllItemsAdmin(String category, String subcategory) {
        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_ITEMS_ADMIN, new Object[]{subcategory, category}, new ItemMapper());
        } catch (DataAccessException e) {
            log.error("Failed to get all items for admin. Exception: {}", e.getMessage());
            throw new ApplicationException("Failed to get all items for admin.");
        }
    }

    @Override
    public void enableDisableItem(String itemId, boolean enabled) {
        try {
            jdbcTemplate.update(ItemQuery.ENABLE_DISABLE_ITEM, enabled == true ? 1 : 0, itemId);
        } catch (DataAccessException e) {
            log.error("Failed to enable/disable item. ItemId: {}, Exception: {}", itemId, e.getMessage());
            throw new ApplicationException("Failed to enable/disable item.");
        }
    }

    @Override
    public boolean deleteSubcategory(String subcategory) {
        int recordsDeleted = 0;

        try {
            recordsDeleted = jdbcTemplate.update(ItemQuery.DELETE_SUBCATEGORY, subcategory);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return recordsDeleted > 0 ? true : false;
    }

    @Override
    public void deleteItem(String itemId) {
        try {
            jdbcTemplate.update(ItemQuery.DELETE_ITEM, itemId);
        } catch (DataAccessException e) {
            log.error("Failed to delete item: {}, Exception: {}", itemId, e.getMessage());
            throw new ApplicationException("Failed to delete item.");
        }
    }

    @Override
    public boolean insertNotification(ItemModel item) {

        int recordsInserted = 0;

        try {
            recordsInserted = jdbcTemplate.update(ItemQuery.INSERT_NOTIFICATION,
                    new Object[]{Constants.READ_NO, Constants.READ_NO, "email", item.getItemId()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return recordsInserted > 0 ? true : false;
    }
}