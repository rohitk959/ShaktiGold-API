package com.rohitrk.shaktigold.dao;

import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;

import java.util.List;

public interface UserDAO {

	UserAccountModel getSingleUserByEmail(String email);

	void registerSingleUser(UserAccountModel userAccount);

	void insertUserProfile(UserAccountModel userAccount);

	void updateUserProfile(UserDetailsModel userDetails, String email);

	UserDetailsModel getSingleUserProfileByEmail(String email);

	void updatePassword(String newEncryptedPassword, String email);

	String getEmailByInvoiceNumber(int invoiceNumber);

	UserDetailsModel getSingleUserProfileByMobile(String mobileNumber);

    void insertUserRoles(String email, String... userRoles);
}
