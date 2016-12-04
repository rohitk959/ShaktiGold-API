package com.rohitrk.shaktigold.validations;

import org.springframework.stereotype.Component;

import com.rohitrk.shaktigold.model.UserAccountModel;

@Component
public class UserValidator {

	public boolean validateLoginFields(UserAccountModel userAccount) {
		if(userAccount.getEmail() == null) {
			return false;
		}
		
		if(userAccount.getPassword() == null) {
			return false;
		}
		
		return true;
	}

	public boolean validateRegistrationFields(UserAccountModel userAccount) {
		
		
		return false;
	}

}
