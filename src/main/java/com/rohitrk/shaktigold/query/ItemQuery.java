package com.rohitrk.shaktigold.query;

public class ItemQuery {

	public static final String INSERT_CATEGORY = "INSERT INTO category (guid, category_name, description) VALUES (UUID(), ?, ?)";
	
	public static final String GET_ALL_CATEGORY = "SELECT category_name, description, record_active FROM category";
	
	public static final String INSERT_SUB_CATEGORY = "INSERT INTO subcategory (guid, subcategory_name, description, category_fk) VALUES (UUID(), ?, ?, (SELECT guid FROM category WHERE category_name = ? ))";

}
