package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="view_history")
public class ViewHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="view_history_code")
	private Integer viewHistoryCode;

	@Column(name="client_code")
	private Integer clientCode;
	
	@Column(name="inn_code")
	private Integer innCode;

	@Column(name="view_history_datetime")
	private java.sql.Timestamp viewHistoryDatetime;
	
	public ViewHistory() {
		
	}
	
	public ViewHistory(Integer clientCode, Integer innCode, java.sql.Timestamp viewHistoryDatetime) {
		super();
		this.clientCode = clientCode;
		this.innCode = innCode;
		this.viewHistoryDatetime = viewHistoryDatetime;
	}

	public Integer getViewHistoryCode() {
		return viewHistoryCode;
	}

	public void setViewHistoryCode(Integer viewHistoryCode) {
		this.viewHistoryCode = viewHistoryCode;
	}

	public Integer getClientCode() {
		return clientCode;
	}

	public void setClientCode(Integer clientCode) {
		this.clientCode = clientCode;
	}

	public Integer getInnCode() {
		return innCode;
	}

	public void setInnCode(Integer innCode) {
		this.innCode = innCode;
	}

	public java.sql.Timestamp getViewHistoryDatetime() {
		return viewHistoryDatetime;
	}

	public void setViewHistoryDatetime(java.sql.Timestamp viewHistoryDatetime) {
		this.viewHistoryDatetime = viewHistoryDatetime;
	}
	
}
