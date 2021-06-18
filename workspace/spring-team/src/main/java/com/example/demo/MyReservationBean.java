package com.example.demo;

public class MyReservationBean {
	private int reservationCode;
	private String innName;
	private String roomName;
	private int innCode;
	private String reviewFlag;
	
	public MyReservationBean() {
		
	}

	public MyReservationBean(int reservationCode, String innName, String roomName) {
		this.reservationCode = reservationCode;
		this.innName = innName;
		this.roomName = roomName;
	}
	
	public MyReservationBean(int reservationCode, String innName, String roomName, int innCode, String reviewFlag) {
		this.reservationCode = reservationCode;
		this.innName = innName;
		this.roomName = roomName;
		this.innCode = innCode;
		this.reviewFlag = reviewFlag;
	}

	public int getInnCode() {
		return innCode;
	}

	public void setInnCode(int innCode) {
		this.innCode = innCode;
	}

	public int getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(int reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getInnName() {
		return innName;
	}

	public void setInnName(String innName) {
		this.innName = innName;
	}

	public String getReviewFlag() {
		return reviewFlag;
	}

	public void setReviewFlag(String reviewFlag) {
		this.reviewFlag = reviewFlag;
	}
	
	
}
