package com.rohitrk.shaktigold.service;

import java.util.List;

import com.rohitrk.shaktigold.model.CategoryModel;

public interface ItemService {

	boolean insertCategory(CategoryModel category);

	List<CategoryModel> getAllCategory();

	boolean insertSubCategory(CategoryModel category);

}
