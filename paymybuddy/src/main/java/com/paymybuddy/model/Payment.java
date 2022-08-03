package com.paymybuddy.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer paymentId;
	
	@Column(name = "id_debitor")
	private Integer debitorId;
	
	@Column(name = "id_creditor")
	private Integer creditorId;
	
	@Column(name = "pay_datetime")
	LocalDateTime datetime;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "amount")
	private float amount;

	
	public Payment() {};
	
	public Payment(Integer paymentId, Integer debitorId, Integer creditorId, LocalDateTime datetime,
			String description, float amount) {
		super();
		this.paymentId = paymentId;
		this.debitorId = debitorId;
		this.creditorId = creditorId;
		this.datetime = datetime;
		this.description = description;
		this.amount = amount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Float.floatToIntBits(amount) == Float.floatToIntBits(other.amount)
				&& Objects.equals(creditorId, other.creditorId) && Objects.equals(datetime, other.datetime)
				&& Objects.equals(debitorId, other.debitorId) && Objects.equals(description, other.description)
				&& Objects.equals(paymentId, other.paymentId);
	}
	
	@Override
	public String toString() {
		return "ID:"+paymentId+", debitorId:"+debitorId+", creditorId:"+creditorId+", datetime:"+datetime+", description:"+description+", amount:"+amount;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public Integer getDebitorId() {
		return debitorId;
	}

	public Integer getCreditorId() {
		return creditorId;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public String getDescription() {
		return description;
	}

	public float getAmount() {
		return amount;
	}

}
