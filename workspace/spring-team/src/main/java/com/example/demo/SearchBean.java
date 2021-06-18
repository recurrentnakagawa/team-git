package com.example.demo;

public class SearchBean {
	private int innCode;
	private String innName;
	private String prefecturesCode;
	private int roomCode;
	private String roomName;
	private String roomPrice;
	private String roomTotal;
	private int resSum;
	
	public SearchBean() {
		
	}
	
	public SearchBean(int innCode, String innName, String prefecturesCode, int roomCode, String roomName, String roomPrice, String roomTotal, int resSum) {
		this.innCode = innCode;
		this.innName = innName;
		this.prefecturesCode = prefecturesCode;
		this.roomCode = roomCode;
		this.roomName = roomName;
		this.roomPrice = roomPrice;
		this.roomTotal = roomTotal;
		this.resSum = resSum;
	}

	public int getInnCode() {
		return innCode;
	}

	public void setInnCode(int innCode) {
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

	public int getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(int roomCode) {
		this.roomCode = roomCode;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(String roomPrice) {
		this.roomPrice = roomPrice;
	}

	public String getRoomTotal() {
		return roomTotal;
	}

	public void setRoomTotal(String roomTotal) {
		this.roomTotal = roomTotal;
	}

	public int getResSum() {
		return resSum;
	}

	public void setResSum(int resSum) {
		this.resSum = resSum;
	}

}
