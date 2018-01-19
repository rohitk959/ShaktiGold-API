package com.rohitrk.shaktigold.model;

import lombok.Data;

import java.util.List;

@Data
public class SubcategoryModel {
	private String subcategoryName;
	private String description;
	private String imgUrl;
	private boolean recordActive;
	private List<SubCategoryProperty> properties;
}
