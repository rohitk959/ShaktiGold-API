package com.rohitrk.shaktigold.service;

import com.rohitrk.shaktigold.model.ItemModel;

import java.util.List;

public interface CartService {
    void addItemToCart(String email, ItemModel item);

    List<ItemModel> getItemsFromCart(String email);

    void removeItemFromCart(String email, int itemId);

    void updateItemQtyInCart(int itemId, int quantity, String email);
}
