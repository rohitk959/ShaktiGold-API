package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.SubcategoryModel;

public class SubCategoryMapper implements RowMapper<SubcategoryModel>{

	@Override
	public SubcategoryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubcategoryModel subcategory = new SubcategoryModel();
		
			SubcategoryModel subCategory = new SubcategoryModel();
			subCategory.setSubcategoryName(rs.getString("subcategory_name"));
			subCategory.setDescription(rs.getString("subcategory_description"));
			subCategory.setImgUrl(rs.getString("img_url"));
			subCategory.setRecordActive(rs.getInt("subcategory_record_active") == 1? true : false);

		return subcategory;
	}

}
