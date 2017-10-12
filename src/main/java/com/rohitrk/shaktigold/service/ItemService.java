package com.rohitrk.shaktigold.service;

import java.util.List;

import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.OrderModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.model.UserAccountModel;

public interface ItemService {

	boolean insertCategory(CategoryModel category);

	List<CategoryModel> getAllCategory();

	boolean insertSubCategory(CategoryModel category);
	
	boolean updateSubCategory(CategoryModel category);

	CategoryModel getAllSubCategory(CategoryModel category);

	boolean registerItem(ItemModel item);

	List<SubCategoryProperty> getItemTemplate(ItemModel item);

	List<ItemModel> getAllItems(ItemModel item);

	ItemModel getItemDetails(ItemModel item);

	boolean putItemToCart(ItemModel item);

	List<ItemModel> getItemsFromCart(ItemModel item);

	boolean deleteItemFromCart(ItemModel item);

	boolean updateItemQtyInCart(ItemModel item);

	boolean placeOrder(ItemModel item);

	boolean updateOrder(OrderModel order);

	List<ItemModel> getAllUserOrder(ItemModel order);

	boolean sendEstimateSms(ItemModel item);

	List<ItemModel> getAllAdminOrder(ItemModel order);

	boolean updateOrderAdmin(ItemModel order);

	UserAccountModel getUserProfileByInvoiceNumber(ItemModel order);

	boolean hasMoreItems(ItemModel item);

	CategoryModel getAllSubCategoryForAdmin(CategoryModel category);

	boolean enableDisableSubcategory(String subcategory, boolean hidden);

	List<ItemModel> getAllItemsAdmin(ItemModel item);

	boolean enableDisableItem(String itemId, boolean hidden);

	boolean deleteSubcategory(String subcategory);

	boolean deleteItem(String itemId);

	boolean sendEstimateDB(ItemModel item);
}
