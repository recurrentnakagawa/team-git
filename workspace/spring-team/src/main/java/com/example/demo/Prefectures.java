package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prefectures")
public class Prefectures {
	@Id
	@Column(name="prefectures_code")
	private String prefecturesCode;

	@Column(name="prefectures_name")
	private String prefecturesName;
	
	@Column(name="rural_code")
	private String ruralCode;
	
	public Prefectures() {
		
	}

	public String getPrefecturesCode() {
		return prefecturesCode;
	}

	public void setPrefecturesCode(String prefecturesCode) {
		this.prefecturesCode = prefecturesCode;
	}

	public String getPrefecturesName() {
		return prefecturesName;
	}

	public void setPrefecturesName(String prefecturesName) {
		this.prefecturesName = prefecturesName;
	}

	public String getRuralCode() {
		return ruralCode;
	}

	public void setRuralCode(String ruralCode) {
		this.ruralCode = ruralCode;
	}
	
}
