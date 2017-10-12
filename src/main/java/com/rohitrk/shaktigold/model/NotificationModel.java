package com.rohitrk.shaktigold.model;

public class NotificationModel {
	private int id;
	private boolean userRead;
	private boolean adminRead;
	private int approxAmount;
	private ItemModel item;
	private UserAccountModel user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isUserRead() {
		return userRead;
	}
	public void setUserRead(boolean userRead) {
		this.userRead = userRead;
	}
	public boolean isAdminRead() {
		return adminRead;
	}
	public void setAdminRead(boolean adminRead) {
		this.adminRead = adminRead;
	}
	public int getApproxAmount() {
		return approxAmount;
	}
	public void setApproxAmount(int approxAmount) {
		this.approxAmount = approxAmount;
	}
	public ItemModel getItem() {
		return item;
	}
	public void setItem(ItemModel item) {
		this.item = item;
	}
	public UserAccountModel getUser() {
		return user;
	}
	public void setUser(UserAccountModel user) {
		this.user = user;
	}
}
