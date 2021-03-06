package com.rohitrk.shaktigold.dao;

import java.util.List;

import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.OrderModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;

public interface ItemDAO {

	boolean insertCategory(CategoryModel category);

	List<CategoryModel> getAllCategory();

	boolean insertSubCategory(CategoryModel category);
	
	boolean insertSubCategoryProperty(CategoryModel category);
	
	boolean updateSubCategory(CategoryModel category);
	
	boolean updateSubCategoryProperty(CategoryModel category);

	CategoryModel getAllSubCategory(String categoryName);

	boolean registerItem(ItemModel item);

	boolean registerItemProperty(ItemModel item);

	List<SubCategoryProperty> getItemTemplate(ItemModel item);

	List<ItemModel> getAllItems(ItemModel item);

	ItemModel getItemDetails(ItemModel item);

	boolean insertItemToCart(ItemModel item);

	List<ItemModel> getItemsFromCart(ItemModel item);

	boolean deleteItemFromCart(ItemModel item);

	boolean updateItemQtyInCart(ItemModel item);

	boolean placeOrder(ItemModel item);

	boolean updateOrder(OrderModel order);

	List<ItemModel> getAllUserOrder(ItemModel order);

	boolean itemExistsInCart(ItemModel item);

	List<ItemModel> getAllAdminOrder(ItemModel order);

	boolean updateOrderAdmin(ItemModel order);

	boolean checkHasMoreItems(ItemModel item);

	int getLatestItemId();

	CategoryModel getAllSubCategoryForAdmin(String categoryName);

	boolean enableDisableSubcategory(String subcategory, boolean hidden);

	List<ItemModel> getAllItemsAdmin(ItemModel item);

	boolean enableDisableItem(String itemId, boolean hidden);

	boolean deleteSubcategory(String subcategory);

	boolean deleteItem(String itemId);

	boolean insertNotification(ItemModel item);
}
