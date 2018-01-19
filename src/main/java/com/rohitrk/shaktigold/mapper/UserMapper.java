package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.UserAccountModel;

public class UserMapper implements RowMapper<UserAccountModel>{

	@Override
	public UserAccountModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserAccountModel userAccount = new UserAccountModel();

		userAccount.setFirstName(rs.getString("first_name"));
		userAccount.setLastName(rs.getString("last_name"));
		userAccount.setEmail(rs.getString("email"));
		userAccount.setPassword(rs.getString("password"));

		return userAccount;
	}
}