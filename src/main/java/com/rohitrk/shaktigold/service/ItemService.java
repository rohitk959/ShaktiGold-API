package com.rohitrk.shaktigold.service;

import java.util.List;

import com.rohitrk.shaktigold.model.*;

public interface ItemService {

	void addItem(String category, String subcategory, ItemModel item);

	List<ItemModel> getAllItemsUser(String category, String subcategory, int limit, int offset);

	ItemModel getItemDetails(int itemId);

	boolean sendEstimateSms(ItemModel item);

	boolean hasMoreItems(String category, String subcategory, int limit, int offset);

	List<ItemModel> getAllItemsAdmin(String category, String Subcategory);

	void enableDisableItem(String itemId, boolean enabled);

	void deleteItem(String itemId);

	boolean sendEstimateDB(ItemModel item);
}
