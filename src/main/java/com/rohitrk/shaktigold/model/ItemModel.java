package com.rohitrk.shaktigold.model;

import lombok.Data;

import java.util.List;

@Data
public class ItemModel {
	private int itemId;
	private String itemName;
	private int quantity;
	private String imgUrl;
	private String orderStatus;
	private String orderDate;
	private String orderCompleteDate;
	private String invoiceNumber;
	private boolean recordActive;
	private List<ItemProperty> itemProperty;
}
