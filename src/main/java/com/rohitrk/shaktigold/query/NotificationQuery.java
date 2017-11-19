package com.rohitrk.shaktigold.query;

public class NotificationQuery {
	
	public static final String GET_USER_NOTIFICATION_COUNT = "SELECT COUNT(*) FROM notifications WHERE user_read = 0 AND approx_amount IS NOT NULL AND user_fk = ( SELECT id from user_account WHERE email = ? )";
	
	public static final String GET_ADMIN_NOTIFICATION_COUNT = "SELECT COUNT(*) FROM notifications WHERE admin_read = 0";

	public static final String GET_USER_NOTIFICATION_LIST = "SELECT noti.id as ID, noti.admin_read as ADMIN_READ, user_read as USER_READ, noti.approx_amount as APPROX_AMOUNT, item.id as ITEM_ID, item.item_name as ITEM_NAME, usr.email As EMAIL FROM notifications noti INNER JOIN item item ON noti.item_fk = item.id INNER JOIN user_account usr ON noti.user_fk = usr.id WHERE approx_amount IS NOT NULL AND usr.email = UPPER(?)";

	public static final String GET_ADMIN_NOTIFICATION_LIST = "SELECT noti.id as ID, noti.admin_read as ADMIN_READ, user_read as USER_READ, noti.approx_amount as APPROX_AMOUNT, item.id as ITEM_ID, item.item_name as ITEM_NAME, usr.email As EMAIL FROM notifications noti INNER JOIN item item ON noti.item_fk = item.id INNER JOIN user_account usr ON noti.user_fk = usr.id";

}
