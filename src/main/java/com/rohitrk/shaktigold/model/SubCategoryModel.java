package com.rohitrk.shaktigold.model;

import lombok.Data;

import java.util.List;

@Data
public class SubCategoryModel {
	private String subcategoryName;
	private String description;
	private String imgUrl;
	private boolean recordActive;
	private List<SubCategoryProperty> properties;
}
