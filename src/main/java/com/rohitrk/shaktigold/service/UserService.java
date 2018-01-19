package com.rohitrk.shaktigold.service;

import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;

import java.util.List;

public interface UserService {

	public boolean userExists(UserAccountModel userAccount);

	public void registerUser(UserAccountModel userAccount);

	public void updateProfile(UserDetailsModel userDetails, String email);

	public void enableLogin(String email, String password);

	public UserAccountModel getUserDetails(String email);

	public void changePassword(UserAccountModel userAccount);

	public String getEmailByInvoiceNumber(int invoiceNumber);

    UserAccountModel getUserProfileByInvoiceNumber(int invoiceNumber);
}
