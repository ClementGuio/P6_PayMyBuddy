package com.paymybuddy.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer transactionId;
	
	@Column(name = "id_debitor")
	private Integer debitorId;
	
	@Column(name = "id_creditor")
	private Integer creditorId;
	
	@Column(name = "tr_datetime")
	LocalDateTime datetime;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "amount")
	private float amount;

}
