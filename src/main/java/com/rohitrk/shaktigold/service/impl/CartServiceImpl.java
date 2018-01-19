package com.rohitrk.shaktigold.service.impl;

import com.rohitrk.shaktigold.dao.CartDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.service.CartService;
import com.rohitrk.shaktigold.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    CartDAO cartDAO;

    @Autowired
    CommonUtil commonUtil;

    @Override
    public void addItemToCart(String email, ItemModel item) {
        if (!cartDAO.itemExistsInCart(email, item.getItemId())) {
            cartDAO.insertItemToCart(email, item);
        }
    }

    @Override
    public List<ItemModel> getItemsFromCart(String email) {
        List<ItemModel> cartItems = cartDAO.fetchItemsFromCart(email);

        for (ItemModel lItem : cartItems) {
            lItem.setImgUrl(commonUtil.contructURL(lItem.getImgUrl()));
        }

        return cartItems;
    }

    @Override
    public void removeItemFromCart(String email, int itemId) {
        if (cartDAO.itemExistsInCart(email, itemId)) {
            cartDAO.deleteItemFromCart(email, itemId);
        } else {
            throw new IllegalArgumentException("Item does not exists in cart.");
        }
    }

    @Override
    public void updateItemQtyInCart(int itemId, int quantity, String email) {
        cartDAO.updateItemQtyInCart(itemId, quantity, email);
    }
}
