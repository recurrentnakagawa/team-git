package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inn")
public class Inn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="inn_code")
	private Integer innCode;

	@Column(name="inn_name")
	private String innName;
	
	@Column(name="prefectures_code")
	private String prefecturesCode;

	@Column(name="inn_address")
	private String innAddress;
	
	@Column(name="inn_access")
	private String innAccess;
	
	@Column(name="inn_checkin_time")
	private String innCheckinTime;

	@Column(name="inn_checkout_time")
	private String innCheckoutTime;
	
	@Column(name="inn_amenity")
	private String innAmenity;
	
	@Column(name="inn_invalid")
	private String innInvalid;
	
	@Column(name="inn_image_url")
	private String innImageUrl;
	
	public Inn() {
		
	}
	
	public Inn(int innCode, String innName, String prefecture, String innAddress, String innAccess, String checkin, String checkout, 
			String innAmenity, String innInvalid, String url) {
		this.innCode = innCode;
		this.innName = innName;
		this.prefecturesCode = prefecture;
		this.innAddress = innAddress;
		this.innAccess = innAccess;
		this.innCheckinTime = checkin;
		this.innCheckoutTime = checkout;
		this.innAmenity = innAmenity;
		this.innInvalid = innInvalid;
		this.innImageUrl = url;
	}

	public Inn(int innCode, String inn_name, String inn_address) {
		this.innCode = innCode;
		this.innName = inn_name;
		this.innAddress = inn_address;
	}

	public Inn(String innName, String prefecture, String innAddress, String innAccess,String checkin, String checkout,
			String innAmenity, String innInvalid, String url) {
		this.innName = innName;
		this.prefecturesCode = prefecture;
		this.innAddress = innAddress;
		this.innAccess = innAccess;
		this.innCheckinTime = checkin;
		this.innCheckoutTime = checkout;
		this.innAmenity = innAmenity;
		this.innInvalid = innInvalid;
		this.innImageUrl = url;
	}

	public Integer getInnCode() {
		return innCode;
	}

	public void setInnCode(Integer innCode) {
		this.innCode = innCode;
	}

	public String getInnName() {
		return innName;
	}

	public void setInnName(String innName) {
		this.innName = innName;
	}

	public String getPrefecturesCode() {
		return prefecturesCode;
	}

	public void setPrefecturesCode(String prefecturesCode) {
		this.prefecturesCode = prefecturesCode;
	}

	public String getInnAddress() {
		return innAddress;
	}

	public void setInnAddress(String innAddress) {
		this.innAddress = innAddress;
	}

	public String getInnAccess() {
		return innAccess;
	}

	public void setInnAccess(String innAccess) {
		this.innAccess = innAccess;
	}

	public String getInnCheckinTime() {
		return innCheckinTime;
	}

	public void setInnCheckinTime(String innCheckinTime) {
		this.innCheckinTime = innCheckinTime;
	}

	public String getInnCheckoutTime() {
		return innCheckoutTime;
	}

	public void setInnCheckoutTime(String innCheckoutTime) {
		this.innCheckoutTime = innCheckoutTime;
	}

	public String getInnAmenity() {
		return innAmenity;
	}

	public void setInnAmenity(String innAmenity) {
		this.innAmenity = innAmenity;
	}

	public String getInnInvalid() {
		return innInvalid;
	}

	public void setInnInvalid(String innInvalid) {
		this.innInvalid = innInvalid;
	}

	public String getInnImageUrl() {
		return innImageUrl;
	}

	public void setInnImageUrl(String innImageUrl) {
		this.innImageUrl = innImageUrl;
	}
	
}
