package com.rohitrk.shaktigold.query;

public class ItemQuery {

	public static final String INSERT_CATEGORY = "INSERT INTO category (guid, category_name, description, img_url) VALUES (UUID(), ?, ?, ?)";
	
	public static final String GET_ALL_CATEGORY = "SELECT category_name, description, img_url, record_active FROM category";
	
	public static final String UPDATE_SUB_CATEGORY = "UPDATE subcategory SET subcategory_name = ?, description = ? WHERE subcategory_name = ? AND category_fk = (SELECT guid FROM category WHERE category_name = ? )";
	
	public static final String INSERT_SUB_CATEGORY = "INSERT INTO subcategory (guid, subcategory_name, description, img_url, category_fk) VALUES (UUID(), ?, ?, ?, (SELECT guid FROM category WHERE category_name = ? ))";
	
	public static final String UPDATE_SUB_CATEGORY_PROPERTY = "UPDATE subcategory_property SET property_name = ?, property_type = ?, property_unit = ? WHERE property_name = ? AND subcategory_fk = (SELECT guid FROM subcategory WHERE subcategory_name = ?))";
	
	public static final String INSERT_SUB_CATEGORY_PROPERTY = "INSERT INTO subcategory_property (guid, property_name, property_type, property_unit, subcategory_fk) VALUES (UUID(), ?, ?, ?, (SELECT guid FROM subcategory WHERE subcategory_name = ?))";

	public static final String GET_ALL_SUB_CATEGORY = "SELECT cat.category_name category_name, cat.description category_description, scat.subcategory_name subcategory_name, scat.description subcategory_description, scat.img_url img_url, scat.record_active subcategory_record_active FROM category cat INNER JOIN subcategory scat on cat.guid = scat.category_fk WHERE cat.category_name = ? AND scat.record_active = 1 ";

	public static final String INSERT_ITEM = "INSERT INTO item (guid, item_name, img_url, subcategory_fk) VALUES (UUID(), ?, ?, (SELECT guid FROM subcategory WHERE subcategory_name = ?))";

	public static final String INSERT_ITEM_PROPERTY = "INSERT INTO item_property (guid, property_value, item_fk, subcategory_property_fk) VALUES (UUID(), ?, (SELECT guid FROM item WHERE item_name = ?), (SELECT sprop.guid FROM subcategory_property sprop INNER JOIN subcategory scat on sprop.subcategory_fk = scat.guid INNER JOIN category cat on scat.category_fk = cat.guid WHERE sprop.property_name = ? AND scat.subcategory_name = ? AND cat.category_name = ?))";

	public static final String GET_ITEM_TEMPLATE = "SELECT sprop.property_name property_name, sprop.property_type property_type, sprop.property_unit property_unit FROM subcategory_property sprop INNER JOIN subcategory scat on sprop.subcategory_fk = scat.guid INNER JOIN category cat on scat.category_fk = cat.guid WHERE scat.subcategory_name = ? AND cat.category_name = ?";

	public static final String GET_ALL_ITEMS = "SELECT item.item_name item_name, item.item_id item_id, item.img_url img_url, item.record_active as record_active FROM item item INNER JOIN subcategory scat ON item.subcategory_fk = scat.guid INNER JOIN category cat ON scat.category_fk = cat.guid WHERE scat.subcategory_name = ? AND cat.category_name = ? AND item.record_active = 1 ORDER BY item_id LIMIT ? OFFSET ?";

	public static final String GET_ITEM_DETAILS = "SELECT item.item_name item_name, item.item_id item_id, item.img_url img_url, iprop.property_value, sprop.property_name,sprop.property_type, sprop.property_unit FROM item item INNER JOIN item_property iprop ON item.guid = iprop.item_fk INNER JOIN subcategory_property sprop ON iprop.subcategory_property_fk = sprop.guid WHERE item.item_id = ?";

	public static final String INSERT_ITEM_TO_CART = "INSERT INTO cart (guid, quantity, item_fk, user_account_fk) values (UUID(), ?, (select guid from item where item_id = ?), (select guid from user_account where email = ?))";

	public static final String GET_ALL_CART_ITEMS = "SELECT item.item_name item_name, item.item_id item_id, item.img_url img_url, cart.quantity quantity FROM item item INNER JOIN cart cart ON item.guid = cart.item_fk INNER JOIN user_account uacc ON uacc.guid = cart.user_account_fk WHERE uacc.email = ?;";

	public static final String DELETE_ITEM_FROM_CART = "DELETE FROM cart WHERE item_fk = (SELECT guid FROM item itm WHERE itm.item_id = ?) AND user_account_fk = (select guid from user_account where email = ?)";

	public static final String UPDATE_ITEM_FROM_CART = "UPDATE cart SET quantity = ? WHERE item_fk = (SELECT guid FROM item WHERE item_id = ?) AND user_account_fk = (select guid from user_account where email = ?)";

	public static final String UPDATE_ORDER_STATUS = "UPDATE order_details SET order_status = ? WHERE invoice_number = ? AND user_account_fk = (select guid from user_account where email = ?) AND order_status != 'CLOSED'";
	
	public static final String GET_ALL_ORDERS_FOR_USER = "SELECT item.item_id, item.item_name, item.img_url, ordd.quantity, ordd.order_status, DATE_FORMAT(ordd.order_date,'%e %b, %Y - %l:%i %p')as order_date, DATE_FORMAT(ordd.order_complete_date,'%e %b, %Y - %l:%i %p')as order_complete_date, ordd.invoice_number, ordd.order_description FROM order_details ordd INNER JOIN item item on ordd.item_fk = item.guid INNER JOIN user_account usra ON ordd.user_account_fk = usra.guid WHERE usra.email = ? ORDER BY ordd.order_date DESC";

	public static final String CHECK_ITEM_EXISTS_IN_CART = "SELECT 1 FROM cart WHERE item_fk = (SELECT guid FROM item WHERE item_id = ?) and user_account_fk = (select guid from user_account where email = ?)";

	public static final String GET_ALL_ORDERS_FOR_ADMIN = "SELECT item.item_id, item.item_name, item.img_url, ordd.quantity, ordd.order_status, DATE_FORMAT(ordd.order_date,'%e %b, %Y - %l:%i %p')as order_date, DATE_FORMAT(ordd.order_complete_date,'%e %b, %Y - %l:%i %p')as order_complete_date, ordd.invoice_number, ordd.order_description FROM order_details ordd INNER JOIN item item on ordd.item_fk = item.guid ORDER BY ordd.order_date DESC";

	public static final String UPDATE_ORDER_STATUS_ADMIN = "UPDATE order_details SET order_status = ? WHERE invoice_number = ?";
	
	public static final String GET_LATEST_ITEM_ID = "SELECT MAX(item_id) FROM item";

	public static final String GET_ALL_SUB_CATEGORY_FOR_ADMIN = "SELECT cat.category_name category_name, cat.description category_description, scat.subcategory_name subcategory_name, scat.description subcategory_description, scat.img_url img_url, scat.record_active subcategory_record_active FROM category cat INNER JOIN subcategory scat on cat.guid = scat.category_fk WHERE cat.category_name = ?";

	public static final String ENABLE_DISABLE_SUBCATEGORY = "UPDATE subcategory SET record_active = ? WHERE subcategory_name = ?";

	public static final String GET_ALL_ITEMS_ADMIN = "SELECT item.item_name item_name, item.item_id item_id, item.img_url img_url, item.record_active record_active FROM item item INNER JOIN subcategory scat ON item.subcategory_fk = scat.guid INNER JOIN category cat ON scat.category_fk = cat.guid WHERE scat.subcategory_name = ? AND cat.category_name = ? ORDER BY item_id";

	public static final String ENABLE_DISABLE_ITEM = "UPDATE item SET record_active = ? WHERE item_id = ?";

	public static final String DELETE_SUBCATEGORY = "DELETE FROM subcategory WHERE subcategory_name = ?";
	
	public static final String DELETE_ITEM = "DELETE FROM item WHERE item_id = ?";

	public static final String INSERT_NOTIFICATION = "INSERT INTO notifications (admin_read, user_read, approx_amount, user_fk, item_fk) values (?, ?, null, (SELECT guid FROM user_account WHERE email = UPPER(?)), (SELECT guid FROM item WHERE item_id = ?))";

}
