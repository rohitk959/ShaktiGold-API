package com.rohitrk.shaktigold.service.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohitrk.shaktigold.dao.UserDAO;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.util.PasswordUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	UserDAO userDAO;

	@Override
	public boolean userExists(UserAccountModel userAccount) {
		UserAccountModel existingUser = new UserAccountModel();
		existingUser = getSingleUserByEmail(userAccount.getEmail());

		if (existingUser != null) {
			return true;
		}

		return false;
	}

	private UserAccountModel getSingleUserByEmail(String email) {
		return userDAO.getSingleUserByEmail(email);
	}
	
	@Override
	public boolean registerUser(UserAccountModel userAccount) {
		boolean result = false;
		try {
			userAccount.setPasswordSalt(PasswordUtil.getSalt());
			userAccount.setPasswordHash(PasswordUtil.hash(userAccount.getPassword(), userAccount.getPasswordSalt()));
			userAccount.setPassword(null);
			
			if(registerSingleUser(userAccount)) {
				userAccount.getUserDetailsModel().setAddressLine1("");
				userAccount.getUserDetailsModel().setState("");
				userAccount.getUserDetailsModel().setCountry("");
				result = userDAO.insertUserProfile(userAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean registerSingleUser(UserAccountModel userAccount) {
		return userDAO.registerSingleUser(userAccount);
	}

	@Override
	public boolean updateProfile(UserAccountModel userAccount) {
		boolean result = false;
		
		 UserDetailsModel user = getSingleUserProfileByEmail(userAccount.getEmail());
		
		if(user == null){
			result = userDAO.insertUserProfile(userAccount);
		} else {
			result = userDAO.updateUserProfile(userAccount);
		}
		
		return result;
	}

	@Override
	public String enableLogin(UserAccountModel userAccount) {
		boolean validPassword = false;
		String sessionId = null;
		UserAccountModel user = new UserAccountModel();
		
		user = getSingleUserByEmail(userAccount.getEmail());
		
		if(user != null) {
			try {
				validPassword = PasswordUtil.check(userAccount.getPassword(), user.getPasswordHash(), user.getPasswordSalt());
				
				if(validPassword) {
					sessionId = UUID.randomUUID().toString();
					userDAO.deleteUserSession(userAccount.getEmail());
					userDAO.createUserSession(userAccount.getEmail(), sessionId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sessionId;
	}
	
	@Override
	public boolean validateUserSession(String email, String sessionId) {
		String storedSessionId = null;
		
		storedSessionId = userDAO.getUserSession(email);
		
		if(storedSessionId != null) {
			if(0 ==  StringUtils.compare(sessionId, storedSessionId)) {
				return true;
			}
		}
		
		return false;
	}
	
	private UserDetailsModel getSingleUserProfileByEmail(String email) {
		
		return userDAO.getSingleUserProfileByEmail(email);
	}

	@Override
	public UserAccountModel getUserDetails(String email) {
		UserAccountModel user = getSingleUserByEmail(email);
		user.setPasswordHash(null);
		user.setPasswordSalt(null);
		user.setUserDetailsModel(userDAO.getSingleUserProfileByEmail(email));
		return user;
	}

	@Override
	public boolean changePassword(UserAccountModel userAccount) {
		boolean validPassword = false;
		boolean passwordChanged = false;
		UserAccountModel user = new UserAccountModel();
		
		user = getSingleUserByEmail(userAccount.getEmail());
		
		if(user != null) {
			try {
				validPassword = PasswordUtil.check(userAccount.getPassword(), user.getPasswordHash(), user.getPasswordSalt());
				
				if(validPassword) {
					userAccount.setPasswordSalt(PasswordUtil.getSalt());
					userAccount.setPasswordHash(PasswordUtil.hash(userAccount.getNewPassword(), userAccount.getPasswordSalt()));
					passwordChanged = userDAO.updatePassword(userAccount);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return passwordChanged;
	}
}