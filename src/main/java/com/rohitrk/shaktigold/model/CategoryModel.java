package com.rohitrk.shaktigold.model;

import java.util.List;

public class CategoryModel {
	//@Expose(serialize=false)
	private String guid;
	private String categoryName;
	private String description;
	//@Expose(serialize=false)
	private boolean recordActive;
	//@Expose(serialize=false)
	private String sessionId;
	//@Expose(serialize=false)
	private String email;
	//@Expose(serialize=false)
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
	public List<SubCategoryModel> getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(List<SubCategoryModel> subcategory) {
		this.subcategory = subcategory;
	}
}
