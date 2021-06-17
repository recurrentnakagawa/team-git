package com.example.demo;

public class conRoomInfoForm {
	private int roomCode;
	private int innCode;
	private String roomName;
	private String roomDetail;
	private String roomPrice;
	private String roomTotal;
	private String roomMax;

	public conRoomInfoForm() {
		
	}

	public conRoomInfoForm(Integer roomCode, Integer innCode, String roomName, String roomDetail,
			String roomPrice, String roomTotal, String roomMax) {
		this.roomCode = roomCode;
		this.innCode = innCode;
		this.roomName = roomName;
		this.roomDetail = roomDetail;
		this.roomPrice = roomPrice;
		this.roomTotal = roomTotal;
		this.roomMax = roomMax;
	}

	public int getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(int roomCode) {
		this.roomCode = roomCode;
	}

	
	public int getInnCode() {
		return innCode;
	}

	public void setInnCode(int innCode) {
		this.innCode = innCode;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomDetail() {
		return roomDetail;
	}

	public void setRoomDetail(String roomDetail) {
		this.roomDetail = roomDetail;
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

	public String getRoomMax() {
		return roomMax;
	}

	public void setRoomMax(String roomMax) {
		this.roomMax = roomMax;
	}

}
