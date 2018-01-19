package com.rohitrk.shaktigold.service;

import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.model.SubcategoryModel;

import java.util.List;

public interface SubcategoryService {
    void addSubcategory(String category, SubcategoryModel subcategories);

    List<SubcategoryModel> getAllSubcategoryUser(String category);

    List<SubCategoryProperty> getItemTemplate(String category, String subcategory);

    List<SubcategoryModel> getAllSubcategoryAdmin(String category);

    void deleteSubcategory(String category, String subcategory);

    void enableDisableSubcategory(String subcategory, boolean enabled);
}
