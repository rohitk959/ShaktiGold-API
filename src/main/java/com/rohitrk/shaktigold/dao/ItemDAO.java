package com.rohitrk.shaktigold.dao;

import java.util.List;

import com.rohitrk.shaktigold.model.*;

public interface ItemDAO {

	void insertItem(String category, String subcategory, ItemModel item);

	void insertItemProperty(String category, String subcategory, ItemModel item);

	List<ItemModel> fetchAllItems(String category, String subcategory, int limit, int offset);

	ItemModel getItemDetails(int itemId);

	boolean checkHasMoreItems(String category, String subcategory, int limit, int offset);

	int getLatestItemId();

	CategoryModel getAllSubCategoryForAdmin(String categoryName);

	boolean enableDisableSubcategory(String subcategory, boolean hidden);

	List<ItemModel> getAllItemsAdmin(String category, String subcategory);

	void enableDisableItem(String itemId, boolean enabled);

	boolean deleteSubcategory(String subcategory);

	void deleteItem(String itemId);

	boolean insertNotification(ItemModel item);
}
