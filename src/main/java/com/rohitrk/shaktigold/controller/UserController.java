package com.rohitrk.shaktigold.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.util.CommonUtil;
import com.rohitrk.shaktigold.util.CustomResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.Base64;

@CrossOrigin
@RestController("userController")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> enableLogin(@RequestHeader("Authorization") String token) {
        String[] credentials = CommonUtil.decryptAuthToken(token);

        userService.enableLogin(credentials[0], credentials[1]);

        return CustomResponseEntity.ok("Login successful.");

    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserAccountModel userAccount, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (!userService.userExists(userAccount)) {
                userService.registerUser(userAccount);
            } else {
                throw new IllegalArgumentException("User Already Exists. Please use different Email / Mobile number for Registration.");
            }
        } else {
            throw new ApplicationException(400, "Bad Request", "Invalid Parameters.", null);
        }

        return CustomResponseEntity.ok("User Registration Successful.");
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token, @Valid @RequestBody UserDetailsModel userDetails, BindingResult bindingResult) {
        String email = CommonUtil.decryptAuthToken(token)[0];

        if (!bindingResult.hasErrors()) {
            userService.updateProfile(userDetails, email);
        } else {
            throw new ApplicationException(400, "Bad Request", "Invalid Parameters.", null);
        }

        return CustomResponseEntity.ok("Profile updated successfully.");
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {
        String email = CommonUtil.decryptAuthToken(token)[0];

        return CustomResponseEntity.ok(userService.getUserDetails(email));
    }

    @RequestMapping(value = "/user/password", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @Valid @RequestBody UserAccountModel userAccount, BindingResult bindingResult) {
        String[] credentials = CommonUtil.decryptAuthToken(token);

        userAccount.setEmail(credentials[0]);
        userAccount.setPassword(credentials[1]);

        if (!bindingResult.hasErrors()) {
            userService.changePassword(userAccount);
        } else {
            throw new IllegalArgumentException("Invalid Parameters.");
        }
        return CustomResponseEntity.ok("Password updated successfully.");
    }

    @RequestMapping(value = "/admin/userProfileByInvoiceNumber/{invoiceNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserProfileByInvoiceNumber(@PathVariable int invoiceNumber) {
        return  CustomResponseEntity.ok(userService.getUserProfileByInvoiceNumber(invoiceNumber));
    }

}