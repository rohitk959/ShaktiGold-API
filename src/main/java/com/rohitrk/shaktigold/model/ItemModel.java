package com.rohitrk.shaktigold.model;

import java.util.List;

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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderCompleteDate() {
		return orderCompleteDate;
	}
	public void setOrderCompleteDate(String orderCompleteDate) {
		this.orderCompleteDate = orderCompleteDate;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public boolean isRecordActive() {
		return recordActive;
	}
	public void setRecordActive(boolean recordActive) {
		this.recordActive = recordActive;
	}
	public List<ItemProperty> getItemProperty() {
		return itemProperty;
	}
	public void setItemProperty(List<ItemProperty> itemProperty) {
		this.itemProperty = itemProperty;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
