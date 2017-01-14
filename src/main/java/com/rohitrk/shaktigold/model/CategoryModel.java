package com.rohitrk.shaktigold.model;

import java.util.List;

public class CategoryModel {
	private String guid;
	private String categoryName;
	private String description;
	private boolean recordActive;
	private String sessionId;
	private String email;
	private String imgUrl;
	private List<SubCategoryModel> subcategory;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isRecordActive() {
		return recordActive;
	}
	public void setRecordActive(boolean recordActive) {
		this.recordActive = recordActive;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public List<SubCategoryModel> getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(List<SubCategoryModel> subcategory) {
		this.subcategory = subcategory;
	}
}
