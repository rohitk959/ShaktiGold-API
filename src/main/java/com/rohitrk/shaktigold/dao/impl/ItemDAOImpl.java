package com.rohitrk.shaktigold.dao.impl;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.mapper.*;
import com.rohitrk.shaktigold.model.*;
import com.rohitrk.shaktigold.query.ItemQuery;
import com.rohitrk.shaktigold.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("itemDao")
public class ItemDAOImpl implements ItemDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insertCategory(CategoryModel category) {
        boolean result = false;

        try {
            jdbcTemplate.update(ItemQuery.INSERT_CATEGORY,
                    new Object[]{category.getCategoryName(), category.getDescription(), category.getImgUrl()});
            result = true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<CategoryModel> getAllCategory() {
        List<CategoryModel> categoryList = null;

        try {
            categoryList = jdbcTemplate.query(ItemQuery.GET_ALL_CATEGORY, new CategoryMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    @Override
    public boolean insertSubCategory(CategoryModel category) {
        int rowsInserted = 0;

        try {
            for (SubCategoryModel subcategory : category.getSubcategory()) {
                rowsInserted += jdbcTemplate.update(ItemQuery.INSERT_SUB_CATEGORY, new Object[]{
                        subcategory.getSubcategoryName(), subcategory.getDescription(), subcategory.getImgUrl(), category.getCategoryName()});
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return rowsInserted == 0 ? false : true;
    }

    @Override
    public boolean insertSubCategoryProperty(CategoryModel category) {
        int rowsInserted = 0;

        try {
            for (SubCategoryModel subcategory : category.getSubcategory()) {
                for (SubCategoryProperty property : subcategory.getProperties()) {
                    rowsInserted += jdbcTemplate.update(ItemQuery.INSERT_SUB_CATEGORY_PROPERTY,
                            new Object[]{property.getName(), property.getType(), property.getUnit(),
                                    subcategory.getSubcategoryName()});
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return rowsInserted == 0 ? false : true;
    }

    @Override
    public boolean updateSubCategory(CategoryModel category) {
        int rowsUpdated = 0;

        try {
            for (SubCategoryModel subcategory : category.getSubcategory()) {
                rowsUpdated += jdbcTemplate.update(ItemQuery.UPDATE_SUB_CATEGORY,
                        new Object[]{subcategory.getSubcategoryName(), subcategory.getDescription(),
                                subcategory.getSubcategoryName(), category.getCategoryName()});
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return rowsUpdated == 0 ? false : true;
    }

    @Override
    public boolean updateSubCategoryProperty(CategoryModel category) {
        int rowsUpdated = 0;

        try {
            for (SubCategoryModel subcategory : category.getSubcategory()) {
                for (SubCategoryProperty property : subcategory.getProperties()) {
                    rowsUpdated += jdbcTemplate.update(ItemQuery.UPDATE_SUB_CATEGORY_PROPERTY,
                            new Object[]{property.getName(), property.getType(), property.getUnit(),
                                    property.getName(), subcategory.getSubcategoryName()});
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return rowsUpdated == 0 ? false : true;
    }

    @Override
    public CategoryModel getAllSubCategory(String categoryName) {

        CategoryModel subcategory = null;

        try {
            subcategory = jdbcTemplate.queryForObject(ItemQuery.GET_ALL_SUB_CATEGORY, new Object[]{categoryName},
                    new SubCategoryMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return subcategory;
    }

    @Override
    public boolean registerItem(ItemModel item) {
        int rowsInserted = 0;

        try {
            rowsInserted = jdbcTemplate.update(ItemQuery.INSERT_ITEM,
                    new Object[]{item.getItemName(), item.getImgUrl(), item.getSubcategoryName()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rowsInserted == 0 ? false : true;
    }

    @Override
    public boolean registerItemProperty(ItemModel item) {
        int rowsInserted = 0;

        try {
            for (ItemProperty prop : item.getItemProperty()) {
                rowsInserted += jdbcTemplate.update(ItemQuery.INSERT_ITEM_PROPERTY, new Object[]{prop.getValue(),
                        item.getItemName(), prop.getName(), item.getSubcategoryName(), item.getCategoryName()});
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rowsInserted == 0 ? false : true;
    }

    @Override
    public List<SubCategoryProperty> getItemTemplate(ItemModel item) {
        List<SubCategoryProperty> templateList = null;

        try {
            templateList = jdbcTemplate.query(ItemQuery.GET_ITEM_TEMPLATE,
                    new Object[]{item.getSubcategoryName(), item.getCategoryName()}, new ItemtemplateMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return templateList;
    }

    @Override
    public List<ItemModel> getAllItems(ItemModel item) {
        List<ItemModel> itemList = null;

        try {
            itemList = jdbcTemplate.query(ItemQuery.GET_ALL_ITEMS, new Object[]{item.getSubcategoryName(),
                    item.getCategoryName(), item.getLimit(), item.getOffset()}, new ItemMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    @Override
    public ItemModel getItemDetails(ItemModel item) {
        ItemModel itemVO = null;

        try {
            itemVO = jdbcTemplate.queryForObject(ItemQuery.GET_ITEM_DETAILS, new Object[]{item.getItemId()},
                    new ItemDetailsMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return itemVO;
    }

    @Override
    public boolean insertItemToCart(ItemModel item) {
        int rowsInserted = 0;

        try {
            rowsInserted += jdbcTemplate.update(ItemQuery.INSERT_ITEM_TO_CART, new Object[]{item.getQuantity(),
                    item.getItemId(), item.getEmail()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rowsInserted == 0 ? false : true;
    }

    @Override
    public List<ItemModel> getItemsFromCart(ItemModel item) {
        List<ItemModel> itemsList = null;

        try {
            itemsList = jdbcTemplate.query(ItemQuery.GET_ALL_CART_ITEMS, new Object[]{item.getEmail()}, new CartItemMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return itemsList;
    }

    @Override
    public boolean deleteItemFromCart(ItemModel item) {
        int rowsDeleted = 0;

        try {
            rowsDeleted += jdbcTemplate.update(ItemQuery.DELETE_ITEM_FROM_CART, new Object[]{item.getItemId(), item.getEmail()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rowsDeleted == 0 ? false : true;
    }

    @Override
    public boolean updateItemQtyInCart(ItemModel item) {
        int rowsUpdated = 0;

        try {
            rowsUpdated += jdbcTemplate.update(ItemQuery.UPDATE_ITEM_FROM_CART, new Object[]{item.getQuantity(), item.getItemId(), item.getEmail()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rowsUpdated == 0 ? false : true;
    }

    @Override
    public boolean placeOrder(ItemModel item) {

        jdbcTemplate.update("CALL place_order(?)", item.getEmail());

        return true;
    }

    @Override
    public boolean updateOrder(OrderModel order) {
        int rowsUpdated = 0;

        try {
            rowsUpdated += jdbcTemplate.update(ItemQuery.UPDATE_ORDER_STATUS, new Object[]{order.getOrderStatus(), order.getItemId(), order.getEmail()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rowsUpdated == 0 ? false : true;
    }

    @Override
    public List<ItemModel> getAllUserOrder(ItemModel order) {
        List<ItemModel> orders = null;

        try {
            orders = jdbcTemplate.query(ItemQuery.GET_ALL_ORDERS_FOR_USER, new Object[]{order.getEmail()}, new OrderUserMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public boolean itemExistsInCart(ItemModel item) {
        boolean itemExists = false;

        try {
            itemExists = jdbcTemplate.queryForObject(ItemQuery.CHECK_ITEM_EXISTS_IN_CART, Boolean.class, new Object[]{item.getItemId(), item.getEmail()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return itemExists;
    }

    @Override
    public List<ItemModel> getAllAdminOrder(ItemModel order) {
        List<ItemModel> orders = null;

        try {
            orders = jdbcTemplate.query(ItemQuery.GET_ALL_ORDERS_FOR_ADMIN, new OrderUserMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public boolean updateOrderAdmin(ItemModel order) {
        int orderUpdated = 0;

        try {
            orderUpdated = jdbcTemplate.update(ItemQuery.UPDATE_ORDER_STATUS_ADMIN, new Object[]{order.getOrderStatus(), order.getInvoiceNumber()});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderUpdated == 1 ? true : false;
    }

    @Override
    public boolean checkHasMoreItems(ItemModel item) {
        List<ItemModel> itemList = null;

        try {
            itemList = jdbcTemplate.query(ItemQuery.GET_ALL_ITEMS, new Object[]{item.getSubcategoryName(),
                    item.getCategoryName(), item.getLimit(), item.getOffset() + item.getLimit()}, new ItemMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return itemList.size() > 0 ? true : false;
    }

    @Override
    public int getLatestItemId() {
        int itemId = 0;

        try {
            itemId = jdbcTemplate.queryForObject(ItemQuery.GET_LATEST_ITEM_ID, Integer.class);
        } catch (DataAccessException | NullPointerException e) {
            //Fresh Item table inserting first item into table
            e.printStackTrace();
        }

        return itemId;
    }

    @Override
    public CategoryModel getAllSubCategoryForAdmin(String categoryName) {
        CategoryModel subcategory = null;

        try {
            subcategory = jdbcTemplate.queryForObject(ItemQuery.GET_ALL_SUB_CATEGORY_FOR_ADMIN, new Object[]{categoryName},
                    new SubCategoryMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

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
    public List<ItemModel> getAllItemsAdmin(ItemModel item) {
        List<ItemModel> itemList = null;

        try {
            itemList = jdbcTemplate.query(ItemQuery.GET_ALL_ITEMS_ADMIN, new Object[]{item.getSubcategoryName(),
                    item.getCategoryName()}, new ItemMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    @Override
    public boolean enableDisableItem(String itemId, boolean hidden) {
        int recordsUpdated = 0;

        try {
            recordsUpdated = jdbcTemplate.update(ItemQuery.ENABLE_DISABLE_ITEM, hidden == true ? 0 : 1, itemId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return recordsUpdated > 0 ? true : false;
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
    public boolean deleteItem(String itemId) {
        int recordsDeleted = 0;

        try {
            recordsDeleted = jdbcTemplate.update(ItemQuery.DELETE_ITEM, itemId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return recordsDeleted > 0 ? true : false;

    }

    @Override
    public boolean insertNotification(ItemModel item) {

        int recordsInserted = 0;

        try {
            recordsInserted = jdbcTemplate.update(ItemQuery.INSERT_NOTIFICATION,
                    new Object[]{Constants.READ_NO, Constants.READ_NO, item.getEmail(), item.getItemId()});
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return recordsInserted > 0 ? true : false;
    }
}