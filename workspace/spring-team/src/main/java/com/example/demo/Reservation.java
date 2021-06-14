package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reservation")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reservation_code")
	private Integer reservationCode;

	@Column(name="client_code")
	private Integer clientCode;
	
	@Column(name="room_code")
	private Integer roomCode;

	@Column(name="reservation_checkin_date")
	private Date reservationCheckinDate;
	
	@Column(name="reservation_checkout_date")
	private Date reservationCheckoutDate;
	
	@Column(name="reservation_room_total")
	private Integer reservationRoomTotal;

	@Column(name="reservation_price")
	private Integer reservationPrice;
	
	@Column(name="reservation_user_total")
	private Integer reservationUserTotal;
	
	public Reservation() {
		
	}

	public Integer getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(Integer reservationCode) {
		this.reservationCode = reservationCode;
	}

	public Integer getClientCode() {
		return clientCode;
	}

	public void setClientCode(Integer clientCode) {
		this.clientCode = clientCode;
	}

	public Integer getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(Integer roomCode) {
		this.roomCode = roomCode;
	}

	public Date getReservationCheckinDate() {
		return reservationCheckinDate;
	}

	public void setReservationCheckinDate(Date reservationCheckinDate) {
		this.reservationCheckinDate = reservationCheckinDate;
	}

	public Date getReservationCheckoutDate() {
		return reservationCheckoutDate;
	}

	public void setReservationCheckoutDate(Date reservationCheckoutDate) {
		this.reservationCheckoutDate = reservationCheckoutDate;
	}

	public Integer getReservationRoomTotal() {
		return reservationRoomTotal;
	}

	public void setReservationRoomTotal(Integer reservationRoomTotal) {
		this.reservationRoomTotal = reservationRoomTotal;
	}

	public Integer getReservationPrice() {
		return reservationPrice;
	}

	public void setReservationPrice(Integer reservationPrice) {
		this.reservationPrice = reservationPrice;
	}

	public Integer getReservationUserTotal() {
		return reservationUserTotal;
	}

	public void setReservationUserTotal(Integer reservationUserTotal) {
		this.reservationUserTotal = reservationUserTotal;
	}
	
}
