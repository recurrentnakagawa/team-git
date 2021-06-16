package com.example.demo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="review_code")
	private Integer reviewCode;

	@Column(name="inn_code")
	private Integer innCode;
	
	@Column(name="review_star")
	private Integer reviewStar;

	@Column(name="review_detail")
	private String reviewDetail;
	
	@Column(name="review_datetime")
	private Timestamp reviewDatetime;
	
	public Review() {
		
	}

	public Integer getReviewCode() {
		return reviewCode;
	}

	public void setReviewCode(Integer reviewCode) {
		this.reviewCode = reviewCode;
	}

	public Integer getInnCode() {
		return innCode;
	}

	public void setInnCode(Integer innCode) {
		this.innCode = innCode;
	}

	public Integer getReviewStar() {
		return reviewStar;
	}

	public void setReviewStar(Integer reviewStar) {
		this.reviewStar = reviewStar;
	}

	public String getReviewDetail() {
		return reviewDetail;
	}

	public void setReviewDetail(String reviewDetail) {
		this.reviewDetail = reviewDetail;
	}

	public Timestamp getReviewDatetime() {
		return reviewDatetime;
	}

	public void setReviewDatetime(Timestamp reviewDatetime) {
		this.reviewDatetime = reviewDatetime;
	}
	
}
