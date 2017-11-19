package com.rohitrk.shaktigold.query;

public class UserQuery {
	public static final String FETCH_USER_ACCOUNT_BY_EMAIL = "SELECT user_account.first_name, user_account.last_name, user_account.email, user_account.role, user_account.password_hash, user_account.password_salt FROM user_account user_account WHERE user_account.email = UPPER(?)";
	
	public static final String INSERT_SINGLE_USER = "INSERT INTO user_account (first_name, last_name, email, role, password_salt, password_hash, created_date) VALUES (?, ?, UPPER(?), 'USER', ?, ?, now()); ";
	
	public static final String INSERT_USER_PROFILE = "INSERT INTO user_details (address_line_1, address_line_2, state, country, mobile_number, alt_mobile_number, landline_number, created_date, updated_date, profile_img, user_account_fk) VALUES (?, ?, ?, ?, ?, ?, ?, now(), now(), ?, (select id from user_account where email = UPPER(?)))";

	public static final String UPDATE_USER_PROFILE = "UPDATE user_details SET address_line_1 = ?, address_line_2 = ?, state = ?, country = ?, mobile_number = ?, alt_mobile_number = ?, landline_number = ?, updated_date = now(), profile_img = ? where user_account_fk in (select id from user_account where email = UPPER(?))";
	
	public static final String DELETE_USER_SESSION = "DELETE FROM user_session WHERE user_account_fk in (SELECT id FROM user_account where email = UPPER(?))";
	
	public static final String CREATE_USER_SESSION = "INSERT INTO user_session (ssid, create_date, user_account_fk) VALUES (?, now(), (SELECT id FROM user_account WHERE email = UPPER(?)))";

	public static final String GET_USER_SESSION = "SELECT ssid FROM user_session where user_account_fk = (SELECT id FROM user_account WHERE email = UPPER(?))";

	public static final String FETCH_USER_DETAILS_BY_EMAIL = "SELECT user_details.address_line_1, user_details.address_line_2, user_details.state, user_details.country, user_details.mobile_number, user_details.alt_mobile_number, user_details.landline_number, user_details.profile_img FROM user_details user_details WHERE user_details.user_account_fk = (SELECT id FROM user_account WHERE email = UPPER(?))";

	public static final String UPDATE_USER_PASSWORD = "UPDATE user_account SET password_hash = ?, password_salt = ? WHERE UPPER(email) = ?";

	public static final String GET_EMAIL_BY_INVOICE_NUMBER = "SELECT email FROM user_account usr INNER JOIN order_details ord ON usr.id = ord.user_account_fk WHERE ord.id = ?";

	public static final String FETCH_USER_DETAILS_BY_MOBILE = "SELECT user_details.address_line_1, user_details.address_line_2, user_details.state, user_details.country, user_details.mobile_number, user_details.alt_mobile_number, user_details.landline_number, user_details.profile_img FROM user_details user_details WHERE user_details.mobile_number = ?";
}
