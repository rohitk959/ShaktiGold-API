package com.rohitrk.shaktigold.dao.impl;

import com.rohitrk.shaktigold.dao.UserDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.mapper.UserDetailsMapper;
import com.rohitrk.shaktigold.mapper.UserMapper;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;
import com.rohitrk.shaktigold.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository("userDao")
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserAccountModel getSingleUserByEmail(String email) {

        UserAccountModel userAccount = null;

        try {
            userAccount = jdbcTemplate.queryForObject(UserQuery.FETCH_USER_ACCOUNT_BY_EMAIL, new Object[]{email}, new UserMapper());
        } catch (DataAccessException e) {
            log.warn("User Not Found. EmailID: {}", email);
        }

        return userAccount;
    }

    @Override
    public void registerSingleUser(UserAccountModel userAccount) {
        try {
            jdbcTemplate.update(UserQuery.INSERT_SINGLE_USER,
                    new Object[]{userAccount.getFirstName(),
                            userAccount.getLastName(),
                            userAccount.getEmail(),
                            userAccount.getPassword()});

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ApplicationException(500, "", "", e);
        }
    }

    @Override
    public void insertUserProfile(UserAccountModel userAccount) {
        try {
            jdbcTemplate.update(UserQuery.INSERT_USER_PROFILE,
                    new Object[]{
                            userAccount.getUserDetailsModel().getAddressLine1(),
                            userAccount.getUserDetailsModel().getAddressLine2(),
                            userAccount.getUserDetailsModel().getState(),
                            userAccount.getUserDetailsModel().getCountry(),
                            userAccount.getUserDetailsModel().getMobileNumber(),
                            userAccount.getUserDetailsModel().getAltMobileNumber(),
                            userAccount.getUserDetailsModel().getLandLineNumber(),
                            userAccount.getUserDetailsModel().getProfileImg(),
                            userAccount.getEmail()
                    });
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ApplicationException(500, "", "", e);
        }
    }

    @Override
    public void updateUserProfile(UserDetailsModel userDetails, String email) {
        try {
            jdbcTemplate.update(UserQuery.UPDATE_USER_PROFILE,
                    new Object[]{
                            userDetails.getAddressLine1(),
                            userDetails.getAddressLine2(),
                            userDetails.getState(),
                            userDetails.getCountry(),
                            userDetails.getMobileNumber(),
                            userDetails.getAltMobileNumber(),
                            userDetails.getLandLineNumber(),
                            userDetails.getProfileImg(),
                            email
                    });
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ApplicationException(500, "", "", e);
        }
    }

    @Override
    public UserDetailsModel getSingleUserProfileByEmail(String email) {
        UserDetailsModel userDetail = null;
        try {
            userDetail = jdbcTemplate.queryForObject(UserQuery.FETCH_USER_DETAILS_BY_EMAIL, new Object[]{email}, new UserDetailsMapper());
        } catch (DataAccessException e) {
            log.error("No user details exists for email ID: {}", email);
            throw new ApplicationException(500, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal Server Error.", e);
        }

        return userDetail;
    }

    @Override
    public void updatePassword(String newEncryptedPassword, String email) {
        try {
            jdbcTemplate.update(UserQuery.UPDATE_USER_PASSWORD, newEncryptedPassword, email);
        } catch (DataAccessException e) {
            log.error("Password update failed. Email ID {} not found.", email);
            throw new ApplicationException(500, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal Server Error.", e);
        }
    }

    @Override
    public String getEmailByInvoiceNumber(int invoiceNumber) {
        String email = null;

        try {
            email = jdbcTemplate.queryForObject(UserQuery.GET_EMAIL_BY_INVOICE_NUMBER, String.class, invoiceNumber);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return email;
    }

    @Override
    public UserDetailsModel getSingleUserProfileByMobile(String mobileNumber) {
        UserDetailsModel userDetail = null;

        try {
            userDetail = jdbcTemplate.queryForObject(UserQuery.FETCH_USER_DETAILS_BY_MOBILE, new Object[]{mobileNumber}, new UserDetailsMapper());
        } catch (DataAccessException e) {
            log.warn("User Profile not found! contactNumber: {}", mobileNumber);
        }

        return userDetail;
    }

    @Override
    public void insertUserRoles(String email, String... userRoles) {
        try {
            for (String role : userRoles) {
                jdbcTemplate.update(UserQuery.INSERT_USER_ROLE, new Object[]{role, email});
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ApplicationException(500, "Internal Server Error.", e.getMessage(), null);
        }
    }
}
