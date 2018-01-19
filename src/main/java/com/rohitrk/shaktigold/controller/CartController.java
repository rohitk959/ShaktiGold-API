package com.rohitrk.shaktigold.controller;

import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.service.CartService;
import com.rohitrk.shaktigold.util.CommonUtil;
import com.rohitrk.shaktigold.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("cartController")
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping(value = "/user/itemToCart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addtemToCart(@RequestHeader("Authorization") String token, @Valid @RequestBody ItemModel item, BindingResult bindingResult) {
        String email = CommonUtil.decryptAuthToken(token)[0];
        if (!bindingResult.hasErrors()) {
            cartService.addItemToCart(email, item);
        }

        return CustomResponseEntity.ok("Item has been added to cart successfully.");
    }

    @RequestMapping(value = "/user/cartItems", method = RequestMethod.GET)
    public ResponseEntity<?> getItemsFromCart(@RequestHeader("Authorization") String token) {
        String email = CommonUtil.decryptAuthToken(token)[0];
        return CustomResponseEntity.ok(cartService.getItemsFromCart(email));
    }

    @RequestMapping(value = "/user/cartItem/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeItemFromCart(@RequestHeader("Authorization") String token, @PathVariable int itemId) {
        String email = CommonUtil.decryptAuthToken(token)[0];
        cartService.removeItemFromCart(email, itemId);
        return CustomResponseEntity.ok("Item has been removed from cart successfully.");
    }

    @RequestMapping(value = "/user/itemQtyInCart/{itemId}/{quantity}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateItemQtyInCart(@RequestHeader("Authorization") String token, @PathVariable int itemId, @PathVariable int quantity) {
        String email = CommonUtil.decryptAuthToken(token)[0];
        cartService.updateItemQtyInCart(itemId, quantity, email);

        return CustomResponseEntity.ok("Item has been successfully updated in cart");
    }


}
