package paymybuddy.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	
	@Id
	//TODO : à quoi sert @GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer accountId;
	
	//TODO : ne pas garder email et password en clair
	@Column(name = "email")
	private String email;
	
	@Column(name = "pword")
	private String password;
	
	@Column(name = "balance")
	private Double balance;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	
	public Account() {};
	//TODO :virer les id des constructeurs
	/**
	 * 
	 * @param accountId
	 * @param email
	 * @param password
	 * @param balance
	 * @param firstname
	 * @param lastname
	 */
	public Account(Integer accountId, String email, String password, Double balance, String firstname, String lastname) {
		super();
		this.accountId = accountId;
		this.email = email;
		this.password = password;
		this.balance = balance;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return /*Objects.equals(accountId, other.accountId) && */Objects.equals(balance, other.balance)
				&& Objects.equals(email, other.email) && Objects.equals(firstname, other.firstname)
				&& Objects.equals(lastname, other.lastname) && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "ID:"+accountId+", Email:"+email+", Pword:"+password+", balance:"+balance+", firstname:"+firstname+", lastname:"+lastname;
	}
	
	//Methods
	public boolean hasBalanceToPay(Double amount) {
		return amount <= balance;
	}
	
	public void withdrawMoney(Double amount) {
		balance -= amount;
	}
	
	public void addMoney(Double amount) {
		balance += amount;
	}
	
	//Getters
	public Integer getUserId() {
		return accountId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Double getBalance() {
		return balance;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

}
