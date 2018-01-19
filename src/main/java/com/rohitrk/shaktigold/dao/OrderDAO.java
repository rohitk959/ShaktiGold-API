package com.rohitrk.shaktigold.dao;

import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.util.OrderStatusEnum;

import java.util.List;

public interface OrderDAO {
    void placeOrder(String email);

    void updateOrderUser(int id, OrderStatusEnum status, String email);

    List<ItemModel> fetchUserOrders(String email);

    List<ItemModel> fetchAdminOrders();

    void updateOrderAdmin(int invoiceNumber, OrderStatusEnum status);
}
