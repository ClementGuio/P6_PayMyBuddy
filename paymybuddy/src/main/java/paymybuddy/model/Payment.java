package paymybuddy.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private Double amount;
	
	@Column(name = "company_fee")
	private Double companyFee;
	
	@Transient
	private String creditorName;
	@Transient
	private String debitorName;
	
	public Payment() {};
	//TODO :virer les id des constructeurs
	/**
	 * 
	 * @param paymentId
	 * @param debitorId
	 * @param creditorId
	 * @param datetime
	 * @param description
	 * @param amount
	 * @param companyFee
	 */
	public Payment(Integer paymentId, Integer debitorId, Integer creditorId, LocalDateTime datetime, String description,
			Double amount, Double companyFee) {
		super();
		this.paymentId = paymentId;
		this.debitorId = debitorId;
		this.creditorId = creditorId;
		this.datetime = datetime;
		this.description = description;
		this.amount = amount;
		this.companyFee = companyFee;
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
		return Objects.equals(amount, other.amount) && Objects.equals(companyFee, other.companyFee)
				&& Objects.equals(creditorId, other.creditorId) && Objects.equals(datetime, other.datetime)
				&& Objects.equals(debitorId, other.debitorId) && Objects.equals(description, other.description);
	}

	@Override
	public String toString() {
		return "ID:"+paymentId+", debitorId:"+debitorId+", creditorId:"+creditorId
				+", datetime:"+datetime+", description:"+description+", amount:"+amount+", company fee:"+companyFee;
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

	public Double getAmount() {
		return amount;
	}
	
	public Double getCompanyFee() {
		return companyFee;
	}
	
	public String getDebitorName() {
		return debitorName;
	}
	
	public String getCreditorName() {
		return creditorName;
	}
	
	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}
	
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

}
