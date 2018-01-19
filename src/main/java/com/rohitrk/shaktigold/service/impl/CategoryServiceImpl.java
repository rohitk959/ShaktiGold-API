package com.rohitrk.shaktigold.service.impl;

import com.rohitrk.shaktigold.dao.CategoryDAO;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public void addCategory(CategoryModel category) {
        categoryDAO.insertCategory(category);
    }

    @Override
    public List<CategoryModel> getAllCategory() {
        return categoryDAO.fetchAllCategory();
    }
}
