package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.ItemModel;

public class OrderUserMapper implements RowMapper<ItemModel> {

	@Override
	public ItemModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemModel order = new ItemModel();
		
		order.setItemId(rs.getInt("item_id"));
		order.setItemName(rs.getString("item_name"));
		order.setImgUrl(rs.getString("img_url"));
		order.setQuantity(rs.getInt("quantity"));
		order.setOrderStatus(rs.getString("order_status"));
		order.setOrderDate(rs.getString("order_date"));
		order.setOrderCompleteDate(rs.getString("order_complete_date") == null? "NA" : rs.getString("order_complete_date"));
		
		return order;
	}

}
