package com.rohitrk.shaktigold.service;

import java.util.List;

import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.OrderModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;

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

}
