package com.paymybuddy.test.integration;

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
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.exception.InsufficientBalanceException;
import com.paymybuddy.exception.NegativeAmountException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.Payment;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.PaymentManager;
import com.paymybuddy.service.PaymentService;

@DataJpaTest
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
	public void updateBDTest() {
		Account debitor = accountService.getAccountWithEmail("one@email.com");
		debitor.withdrawMoney(Double.valueOf(100));
		Account creditor = accountService.getAccountWithEmail("two@email.com");
		creditor.addMoney(Double.valueOf(100));
		Payment payment = new Payment(null, debitor.getUserId(),creditor.getUserId(),LocalDateTime.now(),"updateBDTest",Double.valueOf(5),Double.valueOf(1));
		
		manager.updateBD(debitor, creditor, payment);
		
		assertThat(paymentService.getAllPayments()).contains(payment);
		assertEquals(Double.valueOf(0),accountService.getAccountWithEmail("one@email.com").getBalance());
		assertEquals(Double.valueOf(200),accountService.getAccountWithEmail("two@email.com").getBalance());
	}
		
	@Test
	public void payAndSucceedTest() throws InsufficientBalanceException, NegativeAmountException{
		Account debitor = accountService.getAccountWithEmail("one@email.com");
		Account creditor = accountService.getAccountWithEmail("two@email.com");
		
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
		Account debitor = accountService.getAccountWithEmail("one@email.com");
		Account creditor = accountService.getAccountWithEmail("two@email.com");
		
		assertThrows(InsufficientBalanceException.class,() -> manager.paySomeone(debitor, creditor, "payAndFaileBecauseOfExcessiveAmountTest", Double.valueOf(1000)));
	}

	@Test
	public void payAndFailBecauseOfNegativeAmountTest() throws InsufficientBalanceException, NegativeAmountException{
		Account debitor = accountService.getAccountWithEmail("one@email.com");
		Account creditor = accountService.getAccountWithEmail("two@email.com");
		
		assertThrows(NegativeAmountException.class,() -> manager.paySomeone(debitor, creditor, "payAndFaileBecauseOfNegativeAmountTest", Double.valueOf(-1)));
	}

	@Test
	public void payAndFailBecauseOfNullAmount() throws InsufficientBalanceException, NegativeAmountException{
		Account debitor = accountService.getAccountWithEmail("one@email.com");
		Account creditor = accountService.getAccountWithEmail("two@email.com");
		
		assertThrows(NullPointerException.class,() -> manager.paySomeone(debitor, creditor, "payAndFaileBecauseOfNullAmountTest", null));
	}

}
