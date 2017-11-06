package com.rohitrk.shaktigold.model;

import lombok.Data;

@Data
public class NotificationModel {
	private int id;
	private boolean userRead;
	private boolean adminRead;
	private int approxAmount;
	private ItemModel item;
	private UserAccountModel user;
}
