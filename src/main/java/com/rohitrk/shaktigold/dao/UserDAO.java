package com.rohitrk.shaktigold.dao;

import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;

public interface UserDAO {

	UserAccountModel getSingleUserByEmail(String email);

	boolean registerSingleUser(UserAccountModel userAccount);

	boolean insertUserProfile(UserAccountModel userAccount);

	boolean updateUserProfile(UserAccountModel userAccount);

	boolean createUserSession(String email, String sessionId);

	void deleteUserSession(String email);

	String getUserSession(String email);

	UserDetailsModel getSingleUserProfileByEmail(String email);

	boolean updatePassword(UserAccountModel userAccount);

	String getEmailByInvoiceNumber(String invoiceNumber);

	UserDetailsModel getSingleUserProfileByMobile(String mobileNumber);

}
