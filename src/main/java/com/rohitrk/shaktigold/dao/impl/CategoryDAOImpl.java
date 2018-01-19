package com.rohitrk.shaktigold.dao.impl;

import com.rohitrk.shaktigold.dao.CategoryDAO;
import com.rohitrk.shaktigold.expceptionHandler.ApplicationException;
import com.rohitrk.shaktigold.mapper.CategoryMapper;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.query.ItemQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("categoryDao")
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertCategory(CategoryModel category) {
        try {
            jdbcTemplate.update(ItemQuery.INSERT_CATEGORY,
                    new Object[]{category.getCategoryName(), category.getDescription(), category.getImgUrl()});
            log.debug("Category inserted successfully. Category: {}", category);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<CategoryModel> fetchAllCategory() {
        List<CategoryModel> categoryList = null;

        try {
            return jdbcTemplate.query(ItemQuery.GET_ALL_CATEGORY, new CategoryMapper());
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }
}
