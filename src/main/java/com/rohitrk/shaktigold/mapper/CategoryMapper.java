package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.CategoryModel;

public class CategoryMapper implements RowMapper<CategoryModel> {

	@Override
	public CategoryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoryModel category = new CategoryModel();
		
		category.setCategoryName(rs.getString("category_name"));
		category.setDescription(rs.getString("description"));
		category.setRecordActive(rs.getInt("record_active") == 1? true : false);
		
		return category;
	}

}
