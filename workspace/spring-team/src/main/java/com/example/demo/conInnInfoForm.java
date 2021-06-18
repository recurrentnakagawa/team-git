package com.example.demo;

public class conInnInfoForm {
	private int innCode;
	private String innName;
	private String innAddress;
	private String innAccess;
	private String prefectureCode;
	private String prefecture;
	private String checkinTime;
	private String checkoutTime;
	private String innAmenity;
	private String innInvalid;
	
	public conInnInfoForm() {
	}

	public String getInnName() {
		return innName;
	}

	public void setInnName(String innName) {
		this.innName = innName;
	}

	public String getInnAddress() {
		return innAddress;
	}

	public void setInnAddress(String innAddress) {
		this.innAddress = innAddress;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String PREFECTURE) {
		this.prefecture = PREFECTURE;
	}

	

	public String getInnInvalid() {
		return innInvalid;
	}

	public void setInnInvalid(String innInvalid) {
		this.innInvalid = innInvalid;
	}

	public String getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}

	public String getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getInnAmenity() {
		return innAmenity;
	}

	public void setInnAmenity(String innAmenity) {
		this.innAmenity = innAmenity;
	}

	public String getInnAccess() {
		return innAccess;
	}

	public void setInnAccess(String innAccess) {
		this.innAccess = innAccess;
	}

	public String getPrefectureCode() {
		return prefectureCode;
	}

	public void setPrefectureCode(String prefectureCode) {
		this.prefectureCode = prefectureCode;
	}

	public int getInnCode() {
		return innCode;
	}

	public void setInnCode(int innCode) {
		this.innCode = innCode;
	}
	
	
}