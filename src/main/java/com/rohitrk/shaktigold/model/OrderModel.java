package com.rohitrk.shaktigold.model;

import lombok.Data;

@Data
public class OrderModel {
	private String email;
	private String sessionId;
	private int itemId;
	private int quantity;
	private String orderStatus;
}
