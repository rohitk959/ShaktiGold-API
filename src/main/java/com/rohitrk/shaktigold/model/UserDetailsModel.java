package com.rohitrk.shaktigold.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsModel {

	private String guid;
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
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAltMobileNumber() {
		return altMobileNumber;
	}
	public void setAltMobileNumber(String altMobileNumber) {
		this.altMobileNumber = altMobileNumber;
	}
	public String getLandLineNumber() {
		return landLineNumber;
	}
	public void setLandLineNumber(String landLineNumber) {
		this.landLineNumber = landLineNumber;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
}
