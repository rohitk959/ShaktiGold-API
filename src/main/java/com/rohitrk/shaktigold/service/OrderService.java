package com.rohitrk.shaktigold.service;

import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.util.OrderStatusEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    void placeOrder(String email);

    void updateOrderUser(int id, OrderStatusEnum status, String email);

    List<ItemModel> getUserOrders(String email);

    List<ItemModel> getAdminOrders();

    void updateOrderAdmin(int invoiceNumber, OrderStatusEnum status);
}
