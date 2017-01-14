package com.rohitrk.shaktigold.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.ItemProperty;

public class ItemDetailsMapper implements RowMapper<ItemModel> {

	@Override
	public ItemModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemModel item = new ItemModel();
		List<ItemProperty> propList = new ArrayList<ItemProperty>();

		item.setItemName(rs.getString("item_name"));
		item.setItemId(rs.getInt("item_id"));
		item.setImgUrl(rs.getString("img_url"));
		
		do {
			ItemProperty lProp = new ItemProperty();
			lProp.setName(rs.getString("property_name"));
			lProp.setValue(rs.getString("property_value"));
			lProp.setType(rs.getString("property_type"));
			lProp.setUnit(rs.getString("property_unit"));
			propList.add(lProp);
		} while(rs.next());
		
		item.setItemProperty(propList);
		
		return item;
	}

}
