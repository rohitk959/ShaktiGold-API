package com.rohitrk.shaktigold.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.OrderModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
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
		boolean subcategoryAdded = false;
		boolean subcategoryPropsAdded = false;
		
		subcategoryAdded = itemDAO.insertSubCategory(category);
		
		if(subcategoryAdded) {
			subcategoryPropsAdded = itemDAO.insertSubCategoryProperty(category);
		}
		return  subcategoryAdded && subcategoryPropsAdded;
	}
	
	@Override
	public boolean updateSubCategory(CategoryModel category) {
		boolean subcategoryAdded = false;
		boolean subcategoryPropsAdded = false;
		
		subcategoryAdded = itemDAO.insertSubCategory(category);
		
		if(subcategoryAdded) {
			subcategoryPropsAdded = itemDAO.insertSubCategoryProperty(category);
		}
		return  subcategoryAdded && subcategoryPropsAdded;
	}

	@Override
	public CategoryModel getAllSubCategory(CategoryModel category) {
		CategoryModel categoryVO = itemDAO.getAllSubCategory(category.getCategoryName());
		categoryVO.setCategoryName(category.getCategoryName());
		categoryVO.setDescription(category.getDescription());
		return categoryVO;
	}
	
	@Override
	public boolean registerItem(ItemModel item) {
		boolean itemRegistered = false;
		
		itemRegistered = itemDAO.registerItem(item);
		
		if(itemRegistered) {
			itemDAO.registerItemProperty(item);
		}
		
		return itemRegistered;
	}

	@Override
	public List<SubCategoryProperty> getItemTemplate(ItemModel item) {
		return itemDAO.getItemTemplate(item);
	}

	@Override
	public List<ItemModel> getAllItems(ItemModel item) {
		return itemDAO.getAllItems(item);
	}
	
	@Override
	public ItemModel getItemDetails(ItemModel item) {
		return itemDAO.getItemDetails(item);
	}

	@Override
	public boolean putItemToCart(ItemModel item) {
		return itemDAO.insertItemToCart(item);
	}

	@Override
	public List<ItemModel> getItemsFromCart(ItemModel item) {
		return itemDAO.getItemsFromCart(item);
	}

	@Override
	public boolean deleteItemFromCart(ItemModel item) {
		return itemDAO.deleteItemFromCart(item);
	}

	@Override
	public boolean updateItemQtyInCart(ItemModel item) {
		return itemDAO.updateItemQtyInCart(item);
	}

	@Override
	public boolean placeOrder(ItemModel item) {
		return itemDAO.placeOrder(item);
	}

	@Override
	public boolean updateOrder(OrderModel order) {
		return itemDAO.updateOrder(order);
	}

	@Override
	public List<ItemModel> getAllUserOrder(ItemModel order) {
		return itemDAO.getAllUserOrder(order);
	}
}
