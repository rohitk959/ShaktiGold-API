package com.rohitrk.shaktigold.dao;

import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.model.SubcategoryModel;

import java.util.List;

public interface SubcategoryDAO {
    void insertSubcategory(String categoryName, SubcategoryModel subcategory);

    void insertSubcategoryProperty(String subcategoryName, List<SubCategoryProperty> properties);

    List<SubcategoryModel> fetchSubcategoryUser(String category);

    List<SubCategoryProperty> fetchItemTemplate(String category, String subcategory);

    List<SubcategoryModel> fetchSubcategoryAdmin(String category);

    void deleteSubcategory(String category, String subcategory);

    void enableDisableSubcategory(String subcategory, boolean enabled);
}
