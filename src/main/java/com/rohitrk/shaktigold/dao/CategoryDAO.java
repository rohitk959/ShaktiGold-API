package com.rohitrk.shaktigold.dao;

import com.rohitrk.shaktigold.model.CategoryModel;

import java.util.List;

public interface CategoryDAO {
    void insertCategory(CategoryModel category);

    List<CategoryModel> fetchAllCategory();
}
