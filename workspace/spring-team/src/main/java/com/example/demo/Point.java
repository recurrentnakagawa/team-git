package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="point")
public class Point {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="point_code")
	private Integer pointCode;

	@Column(name="point_total")
	private Integer pointTotal;
	
	public Point() {
	}
	
	public Point(Integer pointCode,Integer pointTotal) {
		this(pointTotal);
		this.pointCode = pointCode;
	}

	public Point(Integer pointTotal) {
		super();
		this.pointTotal = pointTotal;
	}

	public Integer getPointCode() {
		return pointCode;
	}

	public void setPointCode(Integer pointCode) {
		this.pointCode = pointCode;
	}

	public Integer getPointTotal() {
		return pointTotal;
	}

	public void setPointTotal(Integer pointTotal) {
		this.pointTotal = pointTotal;
	}
	
	
}
