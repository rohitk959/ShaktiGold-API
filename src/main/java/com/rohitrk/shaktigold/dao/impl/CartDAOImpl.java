package com.rohitrk.shaktigold.dao.impl;

import com.rohitrk.shaktigold.dao.CartDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.mapper.CartItemMapper;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.query.ItemQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("CartDao")
public class CartDAOImpl implements CartDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean itemExistsInCart(String email, int itemId) {
        try {
            return jdbcTemplate.queryForObject(ItemQuery.CHECK_ITEM_EXISTS_IN_CART, Boolean.class, new Object[]{itemId, email});
        } catch (DataAccessException e) {
            log.warn("Item {} does not exists in cart for {}.", itemId, email);
        }
        return false;
    }

    @Override
    public void insertItemToCart(String email, ItemModel item) {
        try {
            jdbcTemplate.update(ItemQuery.INSERT_ITEM_TO_CART, new Object[]{item.getQuantity(), item.getItemId(), email});
        } catch (DataAccessException e) {
            log.error("Failed to add item to cart. Error -> {}", e.getMessage());
            throw new ApplicationException("Failed to add item to cart.");
        }
    }

    @Override
    public List<ItemModel> fetchItemsFromCart(String email) {
        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_CART_ITEMS, new Object[]{email}, new CartItemMapper());
        } catch (DataAccessException e) {
            log.error("Failed to fetch cart items. Email: {}", email);
            throw new ApplicationException("Failed to fetch cart items.");
        }
    }

    @Override
    public void deleteItemFromCart(String email, int itemId) {
        try {
            jdbcTemplate.update(ItemQuery.DELETE_ITEM_FROM_CART, new Object[]{itemId, email});
        } catch (DataAccessException e) {
            log.error("Failed to delete item from cart. item: {}, email: {}", itemId, email);
            throw new ApplicationException("Failed to remove item from cart.");
        }
    }

    @Override
    public void updateItemQtyInCart(int itemId, int quantity, String email) {
        try {
            jdbcTemplate.update(ItemQuery.UPDATE_ITEM_FROM_CART, new Object[]{quantity, itemId, email});
        } catch (DataAccessException e) {
            throw new ApplicationException("Failed to update cart quantity.");
        }
    }
}
