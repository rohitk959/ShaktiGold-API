package com.rohitrk.shaktigold.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.ItemProperty;
import com.rohitrk.shaktigold.model.OrderModel;
import com.rohitrk.shaktigold.model.SubCategoryModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	ItemDAO itemDAO;
	
	@Value("${host.ipaddress}")
	private String hostName;
	@Value("${host.port}")
	private String port;
	@Value("${host.protocol}")
	private String protocol;

	@Override
	public boolean insertCategory(CategoryModel category) {
		
		return itemDAO.insertCategory(category);
	}

	@Override
	public List<CategoryModel> getAllCategory() {
		List<CategoryModel> categories = itemDAO.getAllCategory();
		
		for(CategoryModel category : categories ) {
			if(null != category.getImgUrl()){
				category.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port +"/ShaktiGold" + category.getImgUrl());
			}
		}
		
		return categories;
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
		
		for(SubCategoryModel subcategory : categoryVO.getSubcategory() ) {
			if(null != subcategory.getImgUrl()){
				subcategory.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port +"/ShaktiGold" + subcategory.getImgUrl());
			}
		}
		
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
		List<ItemModel> items = itemDAO.getAllItems(item);
		
		for(ItemModel lItem : items ) {
			
			ItemModel lItemDetail = getItemDetails(lItem);
			ListIterator<ItemProperty> itemPropIterator = lItemDetail.getItemProperty().listIterator();
			
			while(itemPropIterator.hasNext()) {
				ItemProperty itemProp = itemPropIterator.next();
				if(!itemProp.getName().equalsIgnoreCase("weight")) {
					itemPropIterator.remove();
				}
			}
			
			lItem.setItemProperty(lItemDetail.getItemProperty());
			lItem.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port +"/ShaktiGold" + lItem.getImgUrl());
		}
		
		return items;
	}
	
	@Override
	public ItemModel getItemDetails(ItemModel item) {
		ItemModel lItem = itemDAO.getItemDetails(item);
		lItem.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port +"/ShaktiGold" + lItem.getImgUrl());
		return lItem; 
	}

	@Override
	public boolean putItemToCart(ItemModel item) {
		return itemDAO.insertItemToCart(item);
	}

	@Override
	public List<ItemModel> getItemsFromCart(ItemModel item) {
		List<ItemModel> cartItems = itemDAO.getItemsFromCart(item);
		
		for(ItemModel lItem : cartItems) {
			lItem.setImgUrl(this.protocol + "://" + this.hostName + ":" + this.port +"/ShaktiGold" + lItem.getImgUrl());
		}
		
		return cartItems;
		
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
