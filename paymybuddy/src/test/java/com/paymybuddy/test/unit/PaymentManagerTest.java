package com.paymybuddy.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.paymybuddy.constant.Fee;
import com.paymybuddy.exception.InsufficientBalanceException;
import com.paymybuddy.exception.NegativeAmountException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.Payment;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.PaymentManager;
import com.paymybuddy.service.PaymentService;
import com.paymybuddy.util.PayMyBuddyUtil.ChargeFeesUtil;

public class PaymentManagerTest {

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
	public void executePaymentTest() throws NegativeAmountException{
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
	public void isPossiblePaymentTest() {
		Account debitor = new Account(1,"debitor@email.com","pword",Double.valueOf(10),"firstname","lastname");
		
		assertTrue(manager.isPossiblePayment(debitor, Double.valueOf(5.5)));
	}
	
	@Test
	public void isNotPossiblePayment() {
		Account debitor = new Account(1,"debitor@email.com","pword",Double.valueOf(5.5),"firstname","lastname");
		
		assertFalse(manager.isPossiblePayment(debitor, Double.valueOf(10)));		
	}
	
}
