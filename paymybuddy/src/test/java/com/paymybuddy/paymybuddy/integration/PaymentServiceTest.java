package com.paymybuddy.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.time.LocalDateTime;

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

import paymybuddy.model.Account;
import paymybuddy.model.Payment;
import paymybuddy.service.AccountService;
import paymybuddy.service.PaymentService;

@DataJpaTest
@ContextConfiguration(classes=paymybuddy.PaymybuddyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class PaymentServiceTest {

	@Autowired
	PaymentService service;
	
	@Test
	public void saveTest() {
		Payment p1 = new Payment(null,1000002,1000003,LocalDateTime.of(2020, 1, 1, 1, 0),null,Double.valueOf(5),Double.valueOf(1));
		service.saveOrUpdatePayment(p1);
		
		assertThat(p1.getPaymentId()).isGreaterThan(1000000);
	}

	@Test
	public void getPaymentTest() {
		Payment p1 = service.getPayment(1000001);
		
		assertThat(p1).isNotNull();
	}

	@Test
	public void getPaymentsWithDebitorIdTest() {
		Iterable<Payment> payments = service.getPaymentsWithDebitorId(1000003);
		
		assertThat(payments).hasSize(2);
	}
	
	@Test
	public void getPaymentsWithCreditorIdTest() {
		Iterable<Payment> payments = service.getPaymentsWithCreditorId(1000001);
		
		assertThat(payments).hasSize(2);
	}
	
	@Test
	public void deletePaymentTest() {
		Payment p1 = service.getPayment(1000001);
		service.deletePayment(p1);
		
		assertThat(service.getPayment(1000001)).isNull();
	}
/*	
	@Test
	public void deleteAccountTest() {
		Account a1 = new Account(null,"deleteTest@email.com","new pword",Double.valueOf(1),"firstname","lastname");
		service.saveOrUpdateAccount(a1);
		
		assertThat(service.getAccountWithEmail("deleteTest@email.com")).isNotNull();
		service.deleteAccount("deleteTest@email.com");
		assertThat(service.getAccountWithEmail("deleteTest@email.com")).isNull();
	}
*/
}
