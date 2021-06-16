package com.example.demo;

public class SearchForm {
	private String checkinDate;
	private String checkoutDate;
	private int prefecturesCode;
	private int selPrice;
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

	public int getSelPrice() {
		return selPrice;
	}

	public void setSelPrice(int selPrice) {
		this.selPrice = selPrice;
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
