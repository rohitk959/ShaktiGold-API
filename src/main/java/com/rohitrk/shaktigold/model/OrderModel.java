package com.rohitrk.shaktigold.model;

import lombok.Data;

@Data
public class OrderModel {
	private String email;
	private int itemId;
	private int quantity;
	private String orderStatus;
}
