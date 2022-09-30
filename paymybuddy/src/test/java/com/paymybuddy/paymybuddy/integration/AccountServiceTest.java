package com.paymybuddy.paymybuddy.integration;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import paymybuddy.model.Account;
import paymybuddy.service.AccountService;

@DataJpaTest
@ContextConfiguration(classes=paymybuddy.PaymybuddyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class AccountServiceTest {
	
	@Autowired
	AccountService service;
	
	@Test
	public void saveTest() {
		Account a1 = new Account(null,"saveTest@email.com","pword",Double.valueOf(1),"firstname","lastname");
		service.saveOrUpdateAccount(a1);
		
		assertThat(a1.getUserId()).isGreaterThan(1000000);
	}
	
	@Test
	public void getAccountTest() {
		assertEquals("testone@email.com",service.getAccount(1000001).getEmail());
	}

	@Test
	public void getAccountWithEmailTest() {
		Account a2 = service.getAccountWithEmail("testone@email.com");
		
		assertThat(a2).isNotNull();
	}
	
	@Test
	public void updateAccountTest() {
		Account a1 = new Account(null,"saveTest@email.com","new pword",Double.valueOf(1),"firstname","lastname");
		service.saveOrUpdateAccount(a1);
		Account a2 = service.getAccountWithEmail("saveTest@email.com");
		
		assertEquals("new pword",a2.getPassword());
	}
	
	@Test
	public void getAllAccountsTest() {
		assertThat(service.getAllAccounts()).hasSizeGreaterThanOrEqualTo(3);
	}
	
	@Test
	public void deleteAccountTest() {
		Account a1 = new Account(null,"deleteTest@email.com","new pword",Double.valueOf(1),"firstname","lastname");
		service.saveOrUpdateAccount(a1);
		
		assertThat(service.getAccountWithEmail("deleteTest@email.com")).isNotNull();
		service.deleteAccount("deleteTest@email.com");
		assertThat(service.getAccountWithEmail("deleteTest@email.com")).isNull();
	}

}
