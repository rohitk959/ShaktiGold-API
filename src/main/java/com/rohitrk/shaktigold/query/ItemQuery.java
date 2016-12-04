package com.rohitrk.shaktigold.query;

public class ItemQuery {

	public static final String INSERT_CATEGORY = "INSERT INTO category (guid, category_name, description) VALUES (UUID(), ?, ?)";
	
	public static final String GET_ALL_CATEGORY = "SELECT category_name, description, record_active FROM category";
	
	public static final String UPDATE_SUB_CATEGORY = "UPDATE subcategory SET subcategory_name = ?, description = ? WHERE subcategory_name = ? AND category_fk = (SELECT guid FROM category WHERE category_name = ? )";
	
	public static final String INSERT_SUB_CATEGORY = "INSERT INTO subcategory (guid, subcategory_name, description, category_fk) VALUES (UUID(), ?, ?, (SELECT guid FROM category WHERE category_name = ? ))";
	
	public static final String UPDATE_SUB_CATEGORY_PROPERTY = "UPDATE subcategory_property SET property_name = ?, property_type = ?, property_unit = ? WHERE property_name = ? AND subcategory_fk = (SELECT guid FROM subcategory WHERE subcategory_name = ?))";
	
	public static final String INSERT_SUB_CATEGORY_PROPERTY = "INSERT INTO subcategory_property (guid, property_name, property_type, property_unit, subcategory_fk) VALUES (UUID(), ?, ?, ?, (SELECT guid FROM subcategory WHERE subcategory_name = ?))";

	public static final String GET_ALL_SUB_CATEGORY = "SELECT cat.category_name category_name, cat.description category_description, scat.subcategory_name subcategory_name, scat.description subcategory_description, scat.record_active subcategory_record_active FROM category cat INNER JOIN subcategory scat on cat.guid = scat.category_fk WHERE cat.category_name = ?; ";

	public static final String INSERT_ITEM = "INSERT INTO item (guid, item_name, img_url, subcategory_fk) VALUES (UUID(), ?, ?, (SELECT guid FROM subcategory WHERE subcategory_name = ?))";

	public static final String INSERT_ITEM_PROPERTY = "INSERT INTO item_property (guid, property_value, item_fk, subcategory_property_fk) VALUES (UUID(), ?, (SELECT guid FROM item WHERE item_name = ?), (SELECT sprop.guid FROM subcategory_property sprop INNER JOIN subcategory scat on sprop.subcategory_fk = scat.guid INNER JOIN category cat on scat.category_fk = cat.guid WHERE sprop.property_name = ? AND scat.subcategory_name = ? AND cat.category_name = ?))";

	public static final String GET_ITEM_TEMPLATE = "SELECT sprop.property_name property_name, sprop.property_type property_type, sprop.property_unit property_unit FROM subcategory_property sprop INNER JOIN subcategory scat on sprop.subcategory_fk = scat.guid INNER JOIN category cat on scat.category_fk = cat.guid WHERE scat.subcategory_name = ? AND cat.category_name = ?";

	public static final String GET_ALL_ITEMS = "SELECT item.item_name item_name, item.item_id item_id, item.img_url img_url FROM item item INNER JOIN subcategory scat ON item.subcategory_fk = scat.guid INNER JOIN category cat ON scat.category_fk = cat.guid WHERE scat.subcategory_name = ? AND cat.category_name = ? AND item.record_active = 1 ORDER BY item_id LIMIT ? OFFSET ?";
}
