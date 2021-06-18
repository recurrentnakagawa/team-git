package com.example.demo;

public class SearchForm {
	private String checkinDate;
	private String checkoutDate;
	private int prefecturesCode;
	private int selLowPrice;
	private int selHighPrice;
	private int selPeople;
	private int selRooms;
	
	public SearchForm() {
		
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getPrefecturesCode() {
		return prefecturesCode;
	}

	public void setPrefecturesCode(int prefecturesCode) {
		this.prefecturesCode = prefecturesCode;
	}

	public int getSelLowPrice() {
		return selLowPrice;
	}

	public void setSelLowPrice(int selLowPrice) {
		this.selLowPrice = selLowPrice;
	}
	
	public int getSelHighPrice() {
		return selHighPrice;
	}

	public void setSelHighPrice(int selHighPrice) {
		this.selHighPrice = selHighPrice;
	}

	public int getSelPeople() {
		return selPeople;
	}

	public void setSelPeople(int selPeople) {
		this.selPeople = selPeople;
	}

	public int getSelRooms() {
		return selRooms;
	}

	public void setSelRooms(int selRooms) {
		this.selRooms = selRooms;
	}
	
}
