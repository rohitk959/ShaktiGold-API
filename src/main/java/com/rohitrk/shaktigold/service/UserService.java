package com.rohitrk.shaktigold.service;

import com.rohitrk.shaktigold.model.UserAccountModel;

public interface UserService {

	public boolean userExists(UserAccountModel userAccount);

	public boolean registerUser(UserAccountModel userAccount);

	public boolean updateProfile(UserAccountModel userAccount);

	public String enableLogin(UserAccountModel userAccount);
	
	public boolean validateUserSession(String email, String sessionId);
	
	public UserAccountModel getUserDetails(String email);

	public boolean changePassword(UserAccountModel userAccount);

	public String getEmailByInvoiceNumber(String invoiceNumber);
}
