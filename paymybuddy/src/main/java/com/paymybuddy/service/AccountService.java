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
	
	/**
	 * Save an account or update it if already exists.
	 * @param account
	 * @return saved account
	 */
	public Account saveOrUpdateAccount(Account account) {
		return repo.save(account); 
	}
	
	/**
	 * Get the account with this email.
	 * @param email
	 * @return account with this email, or null if this email isn't registered.
	 */
	public Account getAccountWithEmail(String email) {
		Optional<Account> opt = repo.findByEmail(email);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	/**
	 * Delete the account with this email
	 * @param email
	 */
	public void deleteAccount(String email) {
		repo.deleteByEmail(email);
	}
	
	/**
	 * Get all accounts registered.
	 * @return all accounts registered
	 */
	public Iterable<Account> getAllAccounts(){
		return repo.findAll();
	}
	
	/**
	 * Get account with thid id.
	 * @param Id
	 * @return id's account
	 */
	public Optional<Account> getAccount(Integer Id) {
		return repo.findById(Id);
	}
	
	//TODO: inutile ?
	/*
	public Iterable<Account> getAccountsWithFirstnameAndLastname(String firstname, String lastname){
		return repo.findByFirstnameAndLastname(firstname, lastname);
	}
	*/
}
