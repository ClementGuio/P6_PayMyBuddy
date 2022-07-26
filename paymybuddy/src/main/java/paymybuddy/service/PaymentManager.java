package paymybuddy.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paymybuddy.exception.InsufficientBalanceException;
import paymybuddy.exception.NegativeAmountException;
import paymybuddy.model.Account;
import paymybuddy.model.Payment;
import paymybuddy.util.PayMyBuddyUtil.ChargeFeesUtil;

@Service
public class PaymentManager {
	
	Logger logger = LoggerFactory.getLogger(PaymentManager.class);
	
	@Autowired
	AccountService accountService;
	
	@Autowired 
	PaymentService paymentService; 
	
	public Payment paySomeone(Account debitor, Account creditor, String description, Double amount) throws NegativeAmountException, InsufficientBalanceException{
		
		if (!isPossiblePayment(debitor,amount)) {
			throw new InsufficientBalanceException("Insufficient debitor's balance to pay");
		}
		
		Payment payment = executePayment(debitor, creditor, description,amount);
		updateBD(debitor,creditor,payment);
		
		return payment;
	}
	
	public boolean isPossiblePayment(Account debitor, Double amount) throws NegativeAmountException{
		if (amount<0) {
			throw new NegativeAmountException("You cannot pay negative amounts.");
		}
		return debitor.hasBalanceToPay(amount);
	}

	public Payment executePayment(Account debitor, Account creditor, String description, Double amount) throws NegativeAmountException, InsufficientBalanceException{
		//Prélèvement des frais de l'entreprise
		Double amountCharged = ChargeFeesUtil.amountAfterCharge(amount);
		//Création du Payment
		Payment payment = new Payment(null,debitor.getUserId(),creditor.getUserId(),LocalDateTime.now(),description,amount,ChargeFeesUtil.companyFee(amount));
		//Update balances
		debitor.withdrawMoney(amountCharged);
		creditor.addMoney(amount);
		 
		return payment;
	}
	
	public void updateBD(Account debitor, Account creditor, Payment payment) {
		accountService.saveOrUpdateAccount(creditor);
		accountService.saveOrUpdateAccount(debitor);
		paymentService.saveOrUpdatePayment(payment);
	}
	
}
