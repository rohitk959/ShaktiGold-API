package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.UserDetailsModel;

public class UserDetailsMapper implements RowMapper<UserDetailsModel> {

	@Override
	public UserDetailsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserDetailsModel userDetails = new UserDetailsModel();
		
		userDetails.setAddressLine1(rs.getString("address_line_1"));
		userDetails.setAddressLine2(rs.getString("address_line_2"));
		userDetails.setState(rs.getString("state"));
		userDetails.setCountry(rs.getString("country"));
		userDetails.setMobileNumber(rs.getString("mobile_number"));
		userDetails.setAltMobileNumber(rs.getString("alt_mobile_number"));
		userDetails.setLandLineNumber(rs.getString("landline_number"));
		userDetails.setProfileImg(rs.getString("profile_img"));
		
		return userDetails;
	}

}
