package com.example.demo;

import java.util.Date;

public class NowReservationBean {
	private String innName;
	private String roomName;
	private Date checkinDate;
	private Date checkoutDate;
	private int reservationUserTotal;
	private int reservationRoomTotal;
	private int reservationPrice;
	private String clientName;
	private String clientTel;
	
	public NowReservationBean() {
		
	}

	public NowReservationBean(String innName, String roomName, Date reservationChecinDate,
			Date reservationChecoutDate, int reservationUserTotal, int reservationRoomTotal, int reservationPrice,
			String clientName, String clientTel) {
		this.innName =innName;
		this.roomName = roomName;
		this.checkinDate = reservationChecinDate;
		this.checkoutDate = reservationChecoutDate;
		this.reservationUserTotal = reservationUserTotal;
		this.reservationRoomTotal = reservationRoomTotal;
		this.reservationPrice = reservationPrice;
		this.clientName = clientName;
		this.clientTel = clientTel;
	}

	public String getInnName() {
		return innName;
	}

	public void setInnName(String innName) {
		this.innName = innName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	
	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getReservationRoomTotal() {
		return reservationRoomTotal;
	}

	public void setReservationRoomTotal(int reservationRoomTotal) {
		this.reservationRoomTotal = reservationRoomTotal;
	}

	public int getReservationUserTotal() {
		return reservationUserTotal;
	}

	public void setReservationUserTotal(int reservationUserTotal) {
		this.reservationUserTotal = reservationUserTotal;
	}

	public int getReservationPrice() {
		return reservationPrice;
	}

	public void setReservationPrice(int reservationPrice) {
		this.reservationPrice = reservationPrice;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientTel() {
		return clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}
	
	
}
