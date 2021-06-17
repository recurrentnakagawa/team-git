package com.example.demo;

import java.sql.Timestamp;

public class ViewBean {
	private Integer view_history_code;
	private String client_code;
	private String inn_name;
	private Timestamp view_history_datetime;
	
	public ViewBean() {
		
	}
	
	public ViewBean(Integer view_history_code,String client_code,String inn_name,Timestamp view_history_datetime) {
		this.view_history_code = view_history_code;
		this.client_code = client_code;
		this.inn_name = inn_name;
		this.view_history_datetime = view_history_datetime;
	}

	public Integer getView_history_code() {
		return view_history_code;
	}

	public void setView_history_code(Integer view_history_code) {
		this.view_history_code = view_history_code;
	}

	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	public String getInn_name() {
		return inn_name;
	}
	public void setInn_name(String inn_name) {
		this.inn_name = inn_name;
	}
	public Timestamp getView_history_datetime() {
		return view_history_datetime;
	}
	public void setView_history_datetime(Timestamp view_history_datetime) {
		this.view_history_datetime = view_history_datetime;
	}
	
}
