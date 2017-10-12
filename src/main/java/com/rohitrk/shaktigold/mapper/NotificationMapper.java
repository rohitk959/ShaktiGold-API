package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.NotificationModel;
import com.rohitrk.shaktigold.model.UserAccountModel;

public class NotificationMapper implements RowMapper<NotificationModel>{

	@Override
	public NotificationModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		NotificationModel notify = new NotificationModel();
		notify.setId(rs.getInt("ID"));
		notify.setAdminRead(rs.getInt("ADMIN_READ") == 1 ? true : false);
		notify.setUserRead(rs.getInt("USER_READ") == 1 ? true : false);
		notify.setApproxAmount(rs.getInt("APPROX_AMOUNT"));
		
		ItemModel item = new ItemModel();
		item.setItemId(rs.getInt("ITEM_ID"));
		item.setItemName(rs.getString("ITEM_NAME"));
		
		UserAccountModel user = new UserAccountModel();
		user.setEmail(rs.getString("EMAIL"));
		
		notify.setItem(item);
		notify.setUser(user);
		
		return notify;
	}
}
