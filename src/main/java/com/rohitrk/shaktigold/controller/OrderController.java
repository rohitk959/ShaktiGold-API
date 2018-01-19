package com.rohitrk.shaktigold.controller;

import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.service.OrderService;
import com.rohitrk.shaktigold.util.CommonUtil;
import com.rohitrk.shaktigold.util.CustomResponseEntity;
import com.rohitrk.shaktigold.util.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("orderController")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/user/placeOrder", method = RequestMethod.GET)
    public ResponseEntity<?> placeOrder(@RequestHeader("Authorization") String token) {
        String email = CommonUtil.decryptAuthToken(token)[0];
        orderService.placeOrder(email);
        return CustomResponseEntity.ok("Your order has been placed successfully.");
    }

    @RequestMapping(value = "/user/order/{id}/{status}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateOrderUser(@RequestHeader("Authorization") String token, @PathVariable int id, @PathVariable String status) {
        OrderStatusEnum statusEnum = OrderStatusEnum.valueOf(status);
        String email = CommonUtil.decryptAuthToken(token)[0];
        orderService.updateOrderUser(id, statusEnum, email);
        return CustomResponseEntity.ok("Your Order has been updated successfully.");
    }

    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getUserOrders(@RequestHeader("Authorization") String token) {
        String email = CommonUtil.decryptAuthToken(token)[0];
        return CustomResponseEntity.ok(orderService.getUserOrders(email));
    }


    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getAdminOrders() {
        return CustomResponseEntity.ok(orderService.getAdminOrders());
    }

    @RequestMapping(value = "/admin/order/{invoiceNumber}/{status}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateOrderAdmin(@PathVariable int invoiceNumber, @PathVariable String status) {
        OrderStatusEnum statusEnum = OrderStatusEnum.valueOf(status);
        orderService.updateOrderAdmin(invoiceNumber, statusEnum);
        return CustomResponseEntity.ok("Your order has been updated successfully.");
    }

}
