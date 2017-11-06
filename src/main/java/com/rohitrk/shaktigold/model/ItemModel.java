package com.rohitrk.shaktigold.model;

import lombok.Data;

import java.util.List;

@Data
public class ItemModel {
	private String email;
	private String sessionId;
	private String categoryName;
	private String subcategoryName;
	private String itemName;
	private int itemId;
	private int quantity;
	private String imgUrl;
	private String orderStatus;
	private String orderDate;
	private String orderCompleteDate;
	private String invoiceNumber;
	private boolean recordActive;
	private List<ItemProperty> itemProperty;
	private int limit;
	private int offset;
}
