package com.paymybuddy.service;

import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.Account;
import com.paymybuddy.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository repo;
	
	public Iterable<Account> getAllAccounts(){
		return repo.findAll();
	}
	
	public Optional<Account> getAccount(Integer Id) {
		return repo.findById(Id);
	}
	
	public void addAccount(Account account) {
		repo.save(account);
	}
	
	public Optional<Account> getAccountWithEmail(String email) {
		return repo.findByEmail(email);
	}
	
	public Iterable<Account> getAccountsWithFirstnameAndLastname(String firstname, String lastname){
		return repo.findByFirstnameAndLastname(firstname, lastname);
	}

}
