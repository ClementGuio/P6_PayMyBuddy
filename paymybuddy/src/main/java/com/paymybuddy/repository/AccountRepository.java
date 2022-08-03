package com.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.Account;


@Repository
public interface AccountRepository extends CrudRepository<Account,Integer>{

	public Optional<Account> findByEmail(String email);
	
	public Iterable<Account> findByFirstnameAndLastname(String firstname, String lastname);
}
