package com.rohitrk.shaktigold.dao.impl;

import com.rohitrk.shaktigold.dao.SubcategoryDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.model.SubcategoryModel;
import com.rohitrk.shaktigold.query.ItemQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("subcategoryDao")
public class SubcategoryDAOImpl implements SubcategoryDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void insertSubcategory(String categoryName, SubcategoryModel subcategory) {
        try {
            jdbcTemplate.update(ItemQuery.INSERT_SUB_CATEGORY, new Object[]{
                    subcategory.getSubcategoryName(), subcategory.getDescription(), subcategory.getImgUrl(), categoryName});
        } catch (DataAccessException e) {
            log.error("Failed to insert subcategory - {}. Error - {}", subcategory.getSubcategoryName(), e.getMessage());
            throw new ApplicationException("Failed to insert subcategory.");
        }
    }

    @Override
    public void insertSubcategoryProperty(String subcategoryName, List<SubCategoryProperty> properties) {
        try {
            for (SubCategoryProperty property : properties) {
                jdbcTemplate.update(ItemQuery.INSERT_SUB_CATEGORY_PROPERTY,
                        new Object[]{property.getName(), property.getType(), property.getUnit(), subcategoryName});
            }
        } catch (DataAccessException e) {
            log.error("Failed to insert property {} for subcategory - {}. Error - {}", properties, subcategoryName, e.getMessage());
            throw new ApplicationException("Failed to insert properties for " + subcategoryName + " subcategory.");
        }
    }

    @Override
    public List<SubcategoryModel> fetchSubcategoryUser(String category) {
        // TODO: Convert to BeanPropertyRowMapper
        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_SUB_CATEGORY, new Object[]{category}, new BeanPropertyRowMapper(SubcategoryModel.class));
        } catch (DataAccessException e) {
            log.error("Failed to fetch all subcategories. Error - {}", e.getMessage());
            throw new ApplicationException("Failed to fetch all subcategories.");
        }
    }

    @Override
    public List<SubCategoryProperty> fetchItemTemplate(String category, String subcategory) {
        try {
            return jdbcTemplate.query(ItemQuery.GET_ITEM_TEMPLATE, new Object[]{subcategory, category}, new BeanPropertyRowMapper(SubCategoryProperty.class));
        } catch (DataAccessException e) {
            log.error("Failed to fetch subcategory properties for {} subcategory.", subcategory);
            throw new ApplicationException("Failed to fetch subcategory properties for " + subcategory + " subcategory.");
        }
    }

    @Override
    public List<SubcategoryModel> fetchSubcategoryAdmin(String category) {
        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_SUB_CATEGORY_FOR_ADMIN, new Object[]{category}, new BeanPropertyRowMapper(SubcategoryModel.class));
        } catch (DataAccessException e) {
            log.error("Failed to fetch all subcategories for admin. Error - {}", e.getMessage());
            throw new ApplicationException("Failed to fetch all subcategories for admin.");
        }
    }

    @Override
    public void deleteSubcategory(String category, String subcategory) {
        try {
            jdbcTemplate.update(ItemQuery.DELETE_SUBCATEGORY, subcategory, category);
        } catch (DataAccessException e) {
            log.error("Failed to delete subcategory. Error: {}", e.getMessage());
            throw new ApplicationException("Failed to delete subcategory.");
        }
    }

    @Override
    public void enableDisableSubcategory(String subcategory, boolean enabled) {
        try {
            jdbcTemplate.update(ItemQuery.ENABLE_DISABLE_SUBCATEGORY, enabled == true ? 1 : 0, subcategory);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateSubCategory(CategoryModel category) {
        /*try {
            for (SubcategoryModel subcategory : category.getSubcategory()) {
                jdbcTemplate.update(ItemQuery.UPDATE_SUB_CATEGORY,
                        new Object[]{subcategory.getSubcategoryName(), subcategory.getDescription(),
                                subcategory.getSubcategoryName(), category.getCategoryName()});
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }*/
    }

    public void updateSubCategoryProperty(CategoryModel category) {
        /*try {
            for (SubcategoryModel subcategory : category.getSubcategory()) {
                for (SubCategoryProperty property : subcategory.getProperties()) {
                    jdbcTemplate.update(ItemQuery.UPDATE_SUB_CATEGORY_PROPERTY,
                            new Object[]{property.getName(), property.getType(), property.getUnit(),
                                    property.getName(), subcategory.getSubcategoryName()});
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }*/
    }
}
