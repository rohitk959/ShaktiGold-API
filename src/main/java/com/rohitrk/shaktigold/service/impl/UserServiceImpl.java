package com.rohitrk.shaktigold.service.impl;

import com.rohitrk.shaktigold.dao.UserDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean userExists(UserAccountModel userAccount) {
        UserAccountModel existingUser = null;
        existingUser = userDAO.getSingleUserByEmail(userAccount.getEmail());

        if (existingUser != null) {
            return true;
        }

        UserDetailsModel existingUserProfile = userDAO.getSingleUserProfileByMobile(userAccount.getUserDetailsModel().getMobileNumber());

        if (existingUserProfile != null && existingUserProfile.getMobileNumber().equals(userAccount.getUserDetailsModel().getMobileNumber())) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public void registerUser(UserAccountModel userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        userDAO.registerSingleUser(userAccount);

        if (userAccount.getRole().equalsIgnoreCase("admin"))
            userDAO.insertUserRoles(userAccount.getEmail(), Constants.ROLE_USER, Constants.ROLE_ADMIN);
        else
            userDAO.insertUserRoles(userAccount.getEmail(), Constants.ROLE_USER);

        userDAO.insertUserProfile(userAccount);
    }

    @Override
    public void updateProfile(UserDetailsModel userDetails, String email) {
        userDAO.updateUserProfile(userDetails, email);
    }

    @Override
    public void enableLogin(String email, String password) {
        UserAccountModel user = userDAO.getSingleUserByEmail(email);

        if (Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword()))
            throw new ApplicationException(400, "Bad Credentials.", "Invalid email ID or Password.", null);
    }

    @Override
    public UserAccountModel getUserDetails(String email) {
        UserAccountModel user = userDAO.getSingleUserByEmail(email);
        user.setUserDetailsModel(userDAO.getSingleUserProfileByEmail(email));

        user.setPassword(null);
        user.setNewPassword(null);
        user.setRole(null);
        user.setCreatedDate(null);
        user.getUserDetailsModel().setUpdatedDate(null);
        return user;
    }

    @Override
    public void changePassword(UserAccountModel userAccount) {
        UserAccountModel user = userDAO.getSingleUserByEmail(userAccount.getEmail());

        if (Objects.isNull(user))
            throw new ApplicationException(500, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal Server Error", null);

        if (!passwordEncoder.matches(userAccount.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Old Password do not match.");

        userDAO.updatePassword(passwordEncoder.encode(userAccount.getNewPassword()), userAccount.getEmail());
    }

    @Override
    public UserAccountModel getUserProfileByInvoiceNumber(int invoiceNumber) {
        String email = getEmailByInvoiceNumber(invoiceNumber);
        return getUserDetails(email);
    }

    @Override
    public String getEmailByInvoiceNumber(int invoiceNumber) {

        String email = userDAO.getEmailByInvoiceNumber(invoiceNumber);

        return email;
    }
}