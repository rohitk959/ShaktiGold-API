package com.rohitrk.shaktigold.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.mapper.CategoryMapper;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.query.ItemQuery;

@Repository("itemDao")
public class ItemDAOImpl implements ItemDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public boolean insertCategory(CategoryModel category) {
		boolean result = false;
		
		try{
			jdbcTemplate.update(ItemQuery.INSERT_CATEGORY, new Object[] {category.getCategoryName(), category.getDescription()});
			result = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<CategoryModel> getAllCategory() {
		List<CategoryModel> categoryList = null;
		
		try {
			categoryList = jdbcTemplate.query(ItemQuery.GET_ALL_CATEGORY, new CategoryMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return categoryList;
	}

	@Override
	public boolean insertSubCategory(CategoryModel category) {
		int rowsInserted = 0;
		
		try{
			rowsInserted = jdbcTemplate.update(ItemQuery.INSERT_SUB_CATEGORY,
					new Object[] { category.getSubcategory().get(0).getSubcategoryName(),
							category.getSubcategory().get(0).getDescription(), category.getCategoryName() });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowsInserted == 0 ? false : true;
	}
}
