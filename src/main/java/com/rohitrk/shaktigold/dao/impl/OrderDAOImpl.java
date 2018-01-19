package com.rohitrk.shaktigold.dao.impl;

import com.rohitrk.shaktigold.dao.OrderDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.mapper.OrderUserMapper;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.query.ItemQuery;
import com.rohitrk.shaktigold.util.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("orderDao")
public class OrderDAOImpl implements OrderDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void placeOrder(String email) {
        jdbcTemplate.update("CALL place_order(?)", email);
    }

    @Override
    public void updateOrderUser(int id, OrderStatusEnum status, String email) {
        try {
            jdbcTemplate.update(ItemQuery.UPDATE_ORDER_STATUS, new Object[]{status.name(), id, email});
        } catch (DataAccessException e) {
            log.error("Error updating order status for user: {}, ItemId: {}, status: {}, Exception: {}", email, id, status.name(), e.getMessage());
            throw new ApplicationException("Failed to update order status.");
        }
    }

    @Override
    public List<ItemModel> fetchUserOrders(String email) {
        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_ORDERS_FOR_USER, new Object[]{email}, new OrderUserMapper());
        } catch (DataAccessException e) {
            log.error("Failed to fetch all user orders. Email: {}, Exception: {}", email, e.getMessage());
            throw new ApplicationException("Failed to fetch all user orders.");
        }
    }

    @Override
    public List<ItemModel> fetchAdminOrders() {
        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_ORDERS_FOR_ADMIN, new OrderUserMapper());
        } catch (DataAccessException e) {
            log.error("Failed to get admin order list. Exception: {}",e.getMessage());
            throw new ApplicationException("Failed to get admin order list.");
        }
    }

    @Override
    public void updateOrderAdmin(int invoiceNumber, OrderStatusEnum statusEnum) {
        try {
            jdbcTemplate.update(ItemQuery.UPDATE_ORDER_STATUS_ADMIN, new Object[]{statusEnum.status(), invoiceNumber});
        } catch (Exception e) {
            log.error("Error updating order status for Admin InvoiceNumber: {}, status: {}, Exception: {}", invoiceNumber, statusEnum.status(), e.getMessage());
        }
    }
}
