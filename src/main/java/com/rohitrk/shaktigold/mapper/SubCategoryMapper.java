package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.SubCategoryModel;

public class SubCategoryMapper implements RowMapper<CategoryModel>{

	@Override
	public CategoryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoryModel category = new CategoryModel();
		List<SubCategoryModel> subcategoryList = new ArrayList<SubCategoryModel>();
		
		do {
			category.setCategoryName(rs.getString("category_name"));
			category.setDescription(rs.getString("category_description"));
			
			SubCategoryModel subCategory = new SubCategoryModel();
			subCategory.setSubcategoryName(rs.getString("subcategory_name"));
			subCategory.setDescription(rs.getString("subcategory_description"));
			subCategory.setRecordActive(rs.getInt("subcategory_record_active") == 1? true : false);
			subcategoryList.add(subCategory);
		} while(rs.next());
		
		category.setSubcategory(subcategoryList);
		return category;
	}

}
