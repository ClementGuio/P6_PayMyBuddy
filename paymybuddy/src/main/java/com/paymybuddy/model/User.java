package com.paymybuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User {

	
	@Id
	//TODO : à quoi sert @GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer userId;
	
	
	//TODO : ne pas garder email et password en clair
	@Column(name = "email")
	private String email;
	
	@Column(name = "pword")
	private String password;
	
	@Column(name = "balance")
	private float balance;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "listOfIds")
	private String listOfIds;
}
