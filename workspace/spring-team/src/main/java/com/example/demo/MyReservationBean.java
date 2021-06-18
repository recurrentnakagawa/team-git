package com.example.demo;

public class MyReservationBean {
	private int reservationCode;
	private String innName;
	private String roomName;
	
	public MyReservationBean() {
		
	}

	public MyReservationBean(int reservationCode, String innName, String roomName) {
		this.reservationCode = reservationCode;
		this.innName = innName;
		this.roomName = roomName;
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
	
	
}
