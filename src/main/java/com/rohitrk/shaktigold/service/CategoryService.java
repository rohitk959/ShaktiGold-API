package com.rohitrk.shaktigold.service;

import com.rohitrk.shaktigold.model.CategoryModel;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryModel category);

    List<CategoryModel> getAllCategory();
}
