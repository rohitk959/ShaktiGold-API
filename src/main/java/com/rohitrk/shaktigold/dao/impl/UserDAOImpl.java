package com.rohitrk.shaktigold.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rohitrk.shaktigold.dao.UserDAO;
import com.rohitrk.shaktigold.mapper.UserDetailsMapper;
import com.rohitrk.shaktigold.mapper.UserMapper;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.model.UserDetailsModel;
import com.rohitrk.shaktigold.query.UserQuery;

@Repository("userDao")
public class UserDAOImpl implements UserDAO{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public UserAccountModel getSingleUserByEmail(String email) {
		
		UserAccountModel userAccount = null;
		
		try {
			userAccount = jdbcTemplate.queryForObject(UserQuery.FETCH_USER_ACCOUNT_BY_EMAIL, new Object[] { email }, new UserMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return userAccount;
	}

	@Override
	public boolean registerSingleUser(UserAccountModel userAccount) {
		boolean result = false;
		try {
			jdbcTemplate.update(UserQuery.INSERT_SINGLE_USER, 
					new Object[] { userAccount.getFirstName(),
							userAccount.getLastName(),
							userAccount.getEmail(),
							userAccount.getPasswordSalt(),
							userAccount.getPasswordHash() });
			
			
			result = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public boolean insertUserProfile(UserAccountModel userAccount) {
		boolean result = false;
		
		try{
			jdbcTemplate.update(UserQuery.INSERT_USER_PROFILE,
					new Object[] {
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
			
			result = true;

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateUserProfile(UserAccountModel userAccount) {
		boolean result = false;
		
		try {
			jdbcTemplate.update(UserQuery.UPDATE_USER_PROFILE,
					new Object[] {
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
			
			result = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public boolean createUserSession(String email, String sessionId) {
		boolean result = false;
		
		try{
			jdbcTemplate.update(UserQuery.CREATE_USER_SESSION, new Object[] { sessionId, email });
			
			result = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void deleteUserSession(String email) {
		try{
			jdbcTemplate.update(UserQuery.DELETE_USER_SESSION, new Object[] { email });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUserSession(String email) {
		String storedSessionId = null;
		
		try {
			storedSessionId = jdbcTemplate.queryForObject(UserQuery.GET_USER_SESSION, new Object[] { email } , String.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return storedSessionId;
	}

	@Override
	public UserDetailsModel getSingleUserProfileByEmail(String email) {
		UserDetailsModel userDetail = null;
		try {
			userDetail = jdbcTemplate.queryForObject(UserQuery.FETCH_USER_DETAILS_BY_EMAIL, new Object[] { email }, new UserDetailsMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return userDetail;
	}

	@Override
	public boolean updatePassword(UserAccountModel userAccount) {
		int rowsUpdated = 0;
		
		try{
			rowsUpdated = jdbcTemplate.update(UserQuery.UPDATE_USER_PASSWORD, userAccount.getPasswordHash(), userAccount.getPasswordSalt(), userAccount.getEmail());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowsUpdated == 1? true : false;
	}
}
