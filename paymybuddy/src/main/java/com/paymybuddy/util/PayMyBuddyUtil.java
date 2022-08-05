package com.paymybuddy.util;

import org.springframework.stereotype.Service;

import com.paymybuddy.constant.Fee;
import com.paymybuddy.exception.NegativeAmountException;
import com.paymybuddy.model.Payment;

@Service
public final class PayMyBuddyUtil {

	public static class ChargeFeesUtil{
	
		public static Double amountAfterCharge(Double amount) throws NegativeAmountException{
			if (amount<0) {
				throw new NegativeAmountException("Negative amount cannot be charged.");
			}
			return Fee.PERCENT_REMAINING_AFTER_FEE * amount;
		}
	
		public static Double companyFee(Double amount) throws NegativeAmountException{
			if (amount<0) {
				throw new NegativeAmountException("Negative amount cannot be charged.");
			}
			return Fee.PERCENT_FEE_CHARGED * amount;
		}
		
		public static Double sumCompanyFees(Iterable<Payment> payments){
			Double sum = 0.0;
			for (Payment payment : payments) {
	 			sum += payment.getCompanyFee();
			}
			return sum;
		}
	}
}
