package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="room")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="room_code")
	private Integer roomCode;

	@Column(name="inn_code")
	private Integer innCode;
	
	@Column(name="room_name")
	private String roomName;

	@Column(name="room_price")
	private Integer roomPrice;
	
	@Column(name="room_detail")
	private String roomDetail;
	
	@Column(name="room_total")
	private Integer roomTotal;

	@Column(name="room_max")
	private Integer roomMax;

	@Column(name="room_image_url")
	private String roomImageUrl;
	
	
	public Room() {
		
	}


	public Integer getRoomCode() {
		return roomCode;
	}


	public void setRoomCode(Integer roomCode) {
		this.roomCode = roomCode;
	}


	public Integer getInnCode() {
		return innCode;
	}


	public void setInnCode(Integer innCode) {
		this.innCode = innCode;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public Integer getRoomPrice() {
		return roomPrice;
	}


	public void setRoomPrice(Integer roomPrice) {
		this.roomPrice = roomPrice;
	}


	public String getRoomDetail() {
		return roomDetail;
	}


	public void setRoomDetail(String roomDetail) {
		this.roomDetail = roomDetail;
	}


	public Integer getRoomTotal() {
		return roomTotal;
	}


	public void setRoomTotal(Integer roomTotal) {
		this.roomTotal = roomTotal;
	}


	public Integer getRoomMax() {
		return roomMax;
	}


	public void setRoomMax(Integer roomMax) {
		this.roomMax = roomMax;
	}


	public String getRoomImageUrl() {
		return roomImageUrl;
	}


	public void setRoomImageUrl(String roomImageUrl) {
		this.roomImageUrl = roomImageUrl;
	}
	
}
