package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_code")
	private Integer questionCode;

	@Column(name = "question_content")
	private String questionContent;

	public Question() {

	}

	public Integer getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(Integer questionCode) {
		this.questionCode = questionCode;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	
}
