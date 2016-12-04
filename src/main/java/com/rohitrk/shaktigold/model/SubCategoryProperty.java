package com.rohitrk.shaktigold.model;

public class SubCategoryProperty {
	private String name;
	private String type;
	private String unit;
	
	public SubCategoryProperty() {
		// TODO Auto-generated constructor stub
	}
	
	public SubCategoryProperty(String lName, String lType, String lUnit) {
		name = lName;
		type = lType;
		unit = lUnit;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
