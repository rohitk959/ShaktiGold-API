package com.rohitrk.shaktigold.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.mapper.CategoryMapper;
import com.rohitrk.shaktigold.mapper.ItemMapper;
import com.rohitrk.shaktigold.mapper.ItemtemplateMapper;
import com.rohitrk.shaktigold.mapper.SubCategoryMapper;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.ItemProperty;
import com.rohitrk.shaktigold.model.SubCategoryModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
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

		try {
			jdbcTemplate.update(ItemQuery.INSERT_CATEGORY,
					new Object[] { category.getCategoryName(), category.getDescription() });
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

		try {
			for (SubCategoryModel subcategory : category.getSubcategory()) {
				rowsInserted += jdbcTemplate.update(ItemQuery.INSERT_SUB_CATEGORY, new Object[] {
						subcategory.getSubcategoryName(), subcategory.getDescription(), category.getCategoryName() });
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowsInserted == 0 ? false : true;
	}

	@Override
	public boolean insertSubCategoryProperty(CategoryModel category) {
		int rowsInserted = 0;

		try {
			for (SubCategoryModel subcategory : category.getSubcategory()) {
				for (SubCategoryProperty property : subcategory.getProperties()) {
					rowsInserted += jdbcTemplate.update(ItemQuery.INSERT_SUB_CATEGORY_PROPERTY,
							new Object[] { property.getName(), property.getType(), property.getUnit(),
									subcategory.getSubcategoryName() });
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowsInserted == 0 ? false : true;
	}

	@Override
	public boolean updateSubCategory(CategoryModel category) {
		int rowsUpdated = 0;

		try {
			for (SubCategoryModel subcategory : category.getSubcategory()) {
				rowsUpdated += jdbcTemplate.update(ItemQuery.UPDATE_SUB_CATEGORY,
						new Object[] { subcategory.getSubcategoryName(), subcategory.getDescription(),
								subcategory.getSubcategoryName(), category.getCategoryName() });
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowsUpdated == 0 ? false : true;
	}

	@Override
	public boolean updateSubCategoryProperty(CategoryModel category) {
		int rowsUpdated = 0;

		try {
			for (SubCategoryModel subcategory : category.getSubcategory()) {
				for (SubCategoryProperty property : subcategory.getProperties()) {
					rowsUpdated += jdbcTemplate.update(ItemQuery.UPDATE_SUB_CATEGORY_PROPERTY,
							new Object[] { property.getName(), property.getType(), property.getUnit(),
									property.getName(), subcategory.getSubcategoryName() });
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowsUpdated == 0 ? false : true;
	}

	@Override
	public CategoryModel getAllSubCategory(String categoryName) {

		CategoryModel subcategory = null;

		try {
			List<CategoryModel> subcategoryList = jdbcTemplate.query(ItemQuery.GET_ALL_SUB_CATEGORY,
					new Object[] { categoryName }, new SubCategoryMapper());
			if (null != subcategoryList && null != subcategoryList.get(0)) {
				subcategory = subcategoryList.get(0);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return subcategory;
	}

	@Override
	public boolean registerItem(ItemModel item) {
		int rowsInserted = 0;

		try {
			rowsInserted = jdbcTemplate.update(ItemQuery.INSERT_ITEM,
					new Object[] { item.getItemName(), item.getImgUrl(), item.getSubcategoryName() });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return rowsInserted == 0 ? false : true;
	}

	@Override
	public boolean registerItemProperty(ItemModel item) {
		int rowsInserted = 0;

		try {
			for (ItemProperty prop : item.getItemProperty()) {
				rowsInserted += jdbcTemplate.update(ItemQuery.INSERT_ITEM_PROPERTY, new Object[] { prop.getValue(),
						item.getItemName(), prop.getName(), item.getSubcategoryName(), item.getCategoryName() });
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return rowsInserted == 0 ? false : true;
	}

	@Override
	public List<SubCategoryProperty> getItemTemplate(ItemModel item) {
		List<SubCategoryProperty> templateList = null;

		try {
			templateList = jdbcTemplate.query(ItemQuery.GET_ITEM_TEMPLATE,
					new Object[] { item.getSubcategoryName(), item.getCategoryName() },
					new ItemtemplateMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return templateList;
	}

	@Override
	public List<ItemModel> getAllItems(ItemModel item) {
		List<ItemModel> itemList = null;
		
		try {
			itemList = jdbcTemplate.query(ItemQuery.GET_ALL_ITEMS, new Object[] { item.getSubcategoryName(),
					item.getCategoryName(), item.getLimit(), item.getOffset() }, new ItemMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return itemList;
	}
}