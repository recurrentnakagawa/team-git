package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rural")
public class Rural {
	@Id
	@Column(name="rural_code")
	private String ruralCode;

	@Column(name="rural_name")
	private String ruralName;
	
	public Rural() {
		
	}

	public String getRuralCode() {
		return ruralCode;
	}

	public void setRuralCode(String ruralCode) {
		this.ruralCode = ruralCode;
	}

	public String getRuralName() {
		return ruralName;
	}

	public void setRuralName(String ruralName) {
		this.ruralName = ruralName;
	}
	
}
