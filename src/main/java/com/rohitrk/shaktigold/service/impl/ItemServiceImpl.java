package com.rohitrk.shaktigold.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	ItemDAO itemDAO;

	@Override
	public boolean insertCategory(CategoryModel category) {
		
		return itemDAO.insertCategory(category);
	}

	@Override
	public List<CategoryModel> getAllCategory() {
		
		return itemDAO.getAllCategory();
	}

	@Override
	public boolean insertSubCategory(CategoryModel category) {
		return  itemDAO.insertSubCategory(category);
	}

}
