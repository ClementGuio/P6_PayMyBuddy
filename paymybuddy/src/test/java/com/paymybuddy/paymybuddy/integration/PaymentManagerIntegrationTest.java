package com.paymybuddy.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import paymybuddy.exception.InsufficientBalanceException;
import paymybuddy.exception.NegativeAmountException;
import paymybuddy.model.Account;
import paymybuddy.model.Payment;
import paymybuddy.service.AccountService;
import paymybuddy.service.PaymentManager;
import paymybuddy.service.PaymentService;

@DataJpaTest
@ContextConfiguration(classes=paymybuddy.PaymybuddyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class PaymentManagerIntegrationTest {

	@Autowired
	AccountService accountService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PaymentManager manager;

	@Test
	public void updateBDTest() throws InsufficientBalanceException{
		Account debitor = accountService.getAccountWithEmail("testone@email.com");
		debitor.withdrawMoney(Double.valueOf(100));
		Account creditor = accountService.getAccountWithEmail("testtwo@email.com");
		creditor.addMoney(Double.valueOf(100));
		Payment payment = new Payment(null, debitor.getUserId(),creditor.getUserId(),LocalDateTime.now(),"updateBDTest",Double.valueOf(5),Double.valueOf(1));
		
		manager.updateBD(debitor, creditor, payment);
		
		assertThat(paymentService.getAllPayments()).contains(payment);
		assertEquals(Double.valueOf(0),accountService.getAccountWithEmail("testone@email.com").getBalance());
		assertEquals(Double.valueOf(200),accountService.getAccountWithEmail("testtwo@email.com").getBalance());
	}
		
	@Test
	public void payAndSucceedTest() throws InsufficientBalanceException, NegativeAmountException{
		Account debitor = accountService.getAccountWithEmail("testone@email.com");
		Account creditor = accountService.getAccountWithEmail("testtwo@email.com");
		
		Payment payment = manager.paySomeone(debitor, creditor, "payAndSucceedTest", Double.valueOf(100));
		
		Iterable<Payment> payments = paymentService.getAllPayments();
		assertThat(payment).isNotNull();
		assertThat(payments).contains(payment);
		assertEquals(Double.valueOf(95),payment.getAmount());
		assertEquals(Double.valueOf(5),payment.getCompanyFee());
		assertEquals(Double.valueOf(0),debitor.getBalance());
		assertEquals(Double.valueOf(195),creditor.getBalance());
	}

	@Test
	public void payAndFaileBecauseOfExcessiveAmountTest() throws InsufficientBalanceException, NegativeAmountException{
		Account debitor = accountService.getAccountWithEmail("testone@email.com");
		Account creditor = accountService.getAccountWithEmail("testtwo@email.com");
		
		assertThrows(InsufficientBalanceException.class,() -> manager.paySomeone(debitor, creditor, "payAndFaileBecauseOfExcessiveAmountTest", Double.valueOf(1000)));
	}

	@Test
	public void payAndFailBecauseOfNegativeAmountTest() throws InsufficientBalanceException, NegativeAmountException{
		Account debitor = accountService.getAccountWithEmail("testone@email.com");
		Account creditor = accountService.getAccountWithEmail("testtwo@email.com");
		
		assertThrows(NegativeAmountException.class,() -> manager.paySomeone(debitor, creditor, "payAndFaileBecauseOfNegativeAmountTest", Double.valueOf(-1)));
	}

	@Test
	public void payAndFailBecauseOfNullAmount() throws InsufficientBalanceException, NegativeAmountException{
		Account debitor = accountService.getAccountWithEmail("testone@email.com");
		Account creditor = accountService.getAccountWithEmail("testtwo@email.com");
		
		assertThrows(NullPointerException.class,() -> manager.paySomeone(debitor, creditor, "payAndFaileBecauseOfNullAmountTest", null));
	}

}
