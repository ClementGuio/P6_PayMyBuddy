package com.paymybuddy.test.unit;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

import com.paymybuddy.model.Account;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@Mock
	AccountRepository repo;
	
	@InjectMocks
	AccountService service;
	
	@Test
	public void getAccountByIdTest() {
		Optional<Account> a1 = Optional.of(new Account(Integer.valueOf(1),"address1@email.com", "azerty",Double.valueOf(20.0), "firstname", "lastname"));
		when(repo.findById(1)).thenReturn(a1);
		Optional<Account> opt = service.getAccount(1);
		assertThat(opt).isPresent();
	}
	
	@Test
	public void getAllAccountsTest() {
		Account a1 = new Account(Integer.valueOf(1),"one@email.com", "azerty",Double.valueOf(20.0), "firstname", "lastname");
		Account a2 = new Account(Integer.valueOf(2),"two@email.com", "azerty",Double.valueOf(20.0), "firstname", "lastname");
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(a1);
		accounts.add(a2);
		when(repo.findAll()).thenReturn(accounts);
		
		Iterable<Account> found = service.getAllAccounts();
		assertThat(found).hasSize(2);
	}
	
	@Test
	public void getAccountWithEmailTest() {
		Account a1 = new Account(Integer.valueOf(1),"address@email.com", "azerty",Double.valueOf(20.0), "firstname", "lastname");
		when(repo.findByEmail("address@email.com")).thenReturn(Optional.of(a1));
		
		Optional<Account> opt = service.getAccountWithEmail("address@email.com");
		assertThat(opt).isPresent();
	}
	/*
	@Test
	public void getAccountWithFirstnameAndLastnameTest() {
		Account a1 = new Account(Integer.valueOf(1),"address@email.com", "azerty",Double.valueOf(20.0), "firstname", "lastname");
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(a1);
		
		when(repo.findByFirstnameAndLastname(Mockito.anyString(),Mockito.anyString())).thenReturn(accounts);
		assertThat(accounts).hasSize(1);
		assertThat(accounts).contains(a1);
	}
	*/
}
