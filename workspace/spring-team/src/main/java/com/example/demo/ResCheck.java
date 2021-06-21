package com.example.demo;

import java.util.Date;

public class ResCheck {
	private Date checkinDate;
	private Date checkoutDate;
	private int resSum;
	
	public ResCheck() {
		
	}
	
	public ResCheck(int resSum) {
		this.resSum = resSum;
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

	public int getResSum() {
		return resSum;
	}

	public void setResSum(int resSum) {
		this.resSum = resSum;
	}

}
