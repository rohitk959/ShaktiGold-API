package com.rohitrk.shaktigold.dao;

import com.rohitrk.shaktigold.model.ItemModel;

import java.util.List;

public interface CartDAO {
    boolean itemExistsInCart(String email, int itemId);

    void insertItemToCart(String email, ItemModel item);

    List<ItemModel> fetchItemsFromCart(String email);

    void deleteItemFromCart(String email, int itemId);

    void updateItemQtyInCart(int itemId, int quantity, String email);
}
