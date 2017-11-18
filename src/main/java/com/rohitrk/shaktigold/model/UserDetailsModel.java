package com.rohitrk.shaktigold.model;

import lombok.Data;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDetailsModel {
	@NotNull
	private String addressLine1;
	private String addressLine2;
	@Size(min = 3, max = 30)
	private String state;
	@Size(min = 3, max = 30)
	private String country;
	@Size(min = 10, max = 10)
	private String mobileNumber;
	private String altMobileNumber;
	private String landLineNumber;
	private Date createdDate;
	private Date updatedDate;
	private String profileImg;
}
