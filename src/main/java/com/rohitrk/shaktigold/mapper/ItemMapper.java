package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.ItemModel;

public class ItemMapper implements RowMapper<ItemModel>{

	@Override
	public ItemModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemModel item = new ItemModel();
		
		item.setItemName(rs.getString("item_name"));
		item.setItemId(rs.getInt("item_id"));
		item.setImgUrl(rs.getString("img_url"));
		
		return item;
	}

}
