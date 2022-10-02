package com.paymybuddy.paymybuddy.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import paymybuddy.exception.InsufficientBalanceException;
import paymybuddy.exception.NegativeAmountException;
import paymybuddy.model.Account;
import paymybuddy.model.Payment;
import paymybuddy.service.AccountService;
import paymybuddy.service.PaymentManager;
import paymybuddy.service.PaymentService;
import paymybuddy.util.PayMyBuddyUtil.ChargeFeesUtil;

public class PaymentManagerTest {
	//TODO: mock des trucs
	@Mock
	AccountService accountService;
	
	@Mock
	PaymentService paymentService;
	
	@Mock
	ChargeFeesUtil util;
	
	@InjectMocks
	static PaymentManager manager;
	
	@BeforeAll
	public static void setUp() {
		manager = new PaymentManager();
	}
	
	@Test
	public void executePaymentTest() throws NegativeAmountException, InsufficientBalanceException{
		Account debitor = new Account(1,"debitor@email.com","pword",Double.valueOf(10),"firstname","lastname");
		Account creditor = new Account(2,"creditor@email.com","pword",Double.valueOf(10),"firstname","lastname");
		Double amount = Double.valueOf(5);
		Payment payment = manager.executePayment(debitor, creditor, "executePayment test",Double.valueOf(amount));
		
		assertNotNull(payment);
		assertEquals(debitor.getUserId(), payment.getDebitorId());
		assertEquals(creditor.getUserId(), payment.getCreditorId());
		assertEquals("executePayment test",payment.getDescription());
		assertEquals(amount,payment.getAmount()+payment.getCompanyFee());
	}
	
	@Test
	public void executePaymentTestWithNegativeAmount() throws NegativeAmountException{
		Account debitor = new Account(1,"debitor@email.com","pword",Double.valueOf(10),"firstname","lastname");
		Account creditor = new Account(2,"creditor@email.com","pword",Double.valueOf(10),"firstname","lastname");
		Double negativeAmount = Double.valueOf(-5);
		assertThrows(NegativeAmountException.class, () -> manager.executePayment(debitor, creditor, "executePayment test",negativeAmount));
	}
	
	@Test
	public void isPossiblePaymentTest() throws NegativeAmountException{
		Account debitor = new Account(1,"debitor@email.com","pword",Double.valueOf(10),"firstname","lastname");
		
		assertTrue(manager.isPossiblePayment(debitor, Double.valueOf(5.5)));
	}
	
	@Test
	public void isNotPossiblePayment() throws NegativeAmountException {
		Account debitor = new Account(1,"debitor@email.com","pword",Double.valueOf(5.5),"firstname","lastname");
		
		assertFalse(manager.isPossiblePayment(debitor, Double.valueOf(10)));		
	}
	
	@Test
	public void isNotPossibleWithNegativeAmountPayment() throws NegativeAmountException {
		Account debitor = new Account(1,"debitor@email.com","pword",Double.valueOf(5.5),"firstname","lastname");
		
		assertThrows(NegativeAmountException.class,() -> manager.isPossiblePayment(debitor, Double.valueOf(-1)));		
	}
	
	
}
