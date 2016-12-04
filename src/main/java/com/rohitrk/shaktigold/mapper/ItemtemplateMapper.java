package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.SubCategoryProperty;

public class ItemtemplateMapper implements RowMapper<SubCategoryProperty> {

	@Override
	public SubCategoryProperty mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			SubCategoryProperty lProp = new SubCategoryProperty(rs.getString("property_name"),
					rs.getString("property_type"), rs.getString("property_unit"));
		
		return lProp;
	}

}
