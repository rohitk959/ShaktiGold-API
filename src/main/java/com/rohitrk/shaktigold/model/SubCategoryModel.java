package com.rohitrk.shaktigold.model;

import java.util.List;

public class SubCategoryModel {
	private String subcategoryName;
	private String description;
	private boolean recordActive;
	private List<SubCategoryProperty> properties;
	
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<SubCategoryProperty> getProperties() {
		return properties;
	}
	public void setProperties(List<SubCategoryProperty> properties) {
		this.properties = properties;
	}
	public boolean isRecordActive() {
		return recordActive;
	}
	public void setRecordActive(boolean recordActive) {
		this.recordActive = recordActive;
	}
}
