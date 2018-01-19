package com.rohitrk.shaktigold.service.impl;

import com.rohitrk.shaktigold.dao.OrderDAO;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.service.OrderService;
import com.rohitrk.shaktigold.util.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;

    @Override
    public void placeOrder(String email) {
        orderDAO.placeOrder(email);
    }

    @Override
    public void updateOrderUser(int id, OrderStatusEnum statusEnum, String email) {
        if (!statusEnum.isValidOrderStatusUser()) {
            log.warn("Invalid order status: {}", statusEnum.status());
            throw new IllegalArgumentException("Invalid order status.");
        }
        orderDAO.updateOrderUser(id, statusEnum, email);
    }

    @Override
    public List<ItemModel> getUserOrders(String email) {
        return orderDAO.fetchUserOrders(email);
    }

    @Override
    public List<ItemModel> getAdminOrders() {
        return orderDAO.fetchAdminOrders();
    }

    @Override
    public void updateOrderAdmin(int invoiceNumber, OrderStatusEnum statusEnum) {
        if(!statusEnum.isValidOrderStatusAdmin()) {
            log.warn("Invalid order status: {}", statusEnum.status());
            throw new IllegalArgumentException("Invalid order status.");
        }

        orderDAO.updateOrderAdmin(invoiceNumber,statusEnum);
    }
}
