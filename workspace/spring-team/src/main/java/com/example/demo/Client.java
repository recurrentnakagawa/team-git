package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_code")
	private Integer clientCode;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "client_kana")
	private String clientKana;

	@Column(name = "client_email")
	private String clientEmail;

	@Column(name = "client_tel")
	private String clientTel;

	@Column(name = "client_address")
	private String clientAddress;

	@Column(name = "client_password")
	private String clientPassword;

	@Column(name = "client_sex")
	private String clientSex;

	@Column(name = "point_code")
	private Integer pointCode;

	@Column(name = "question_code")
	private Integer questionCode;

	@Column(name = "client_answer")
	private String clientAnswer;

	@Column(name = "role_code")
	private Integer roleCode;

	public Client(Integer clientCode, String clientName, String clientKana, String clientEmail, String clientTel,
			String clientAddress, String clientPassword, String clientSex, Integer pointCode, Integer questionCode,
			String clientAnswer, Integer roleCode) {
		this(clientName, clientKana, clientEmail, clientTel, clientAddress, clientPassword, clientSex, pointCode,
				questionCode, clientAnswer, roleCode);
		this.clientCode = clientCode;
	}

	public Client(String clientName, String clientKana, String clientEmail, String clientTel, String clientAddress,
			String clientPassword, String clientSex, Integer pointCode, Integer questionCode, String clientAnswer,
			Integer roleCode) {
		super();
		this.clientName = clientName;
		this.clientKana = clientKana;
		this.questionCode = questionCode;
		this.clientEmail = clientEmail;
		this.clientTel = clientTel;
		this.clientAddress = clientAddress;
		this.clientPassword = clientPassword;
		this.clientSex = clientSex;
		this.pointCode = pointCode;
		this.questionCode = questionCode;
		this.clientAnswer = clientAnswer;
		this.roleCode = roleCode;
	}

	public Client() {

	}

	public Integer getClientCode() {
		return clientCode;
	}

	public void setClientCode(Integer clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientKana() {
		return clientKana;
	}

	public void setClientKana(String clientKana) {
		this.clientKana = clientKana;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getClientTel() {
		return clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientPassword() {
		return clientPassword;
	}

	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}

	public String getClientSex() {
		return clientSex;
	}

	public void setClientSex(String clientSex) {
		this.clientSex = clientSex;
	}

	public Integer getPointCode() {
		return pointCode;
	}

	public void setPointCode(Integer pointCode) {
		this.pointCode = pointCode;
	}

	public Integer getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(Integer questionCode) {
		this.questionCode = questionCode;
	}

	public String getClientAnswer() {
		return clientAnswer;
	}

	public void setClientAnswer(String clientAnswer) {
		this.clientAnswer = clientAnswer;
	}

	public Integer getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

}
