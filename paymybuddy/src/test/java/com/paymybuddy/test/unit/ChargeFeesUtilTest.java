package com.paymybuddy.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.paymybuddy.constant.Fee;
import com.paymybuddy.exception.NegativeAmountException;
import com.paymybuddy.model.Payment;
import com.paymybuddy.util.PayMyBuddyUtil.ChargeFeesUtil;

public class ChargeFeesUtilTest {
	
	@Test
	public void amountAfterChargeTest() throws NegativeAmountException{
		Double chargedAmount = ChargeFeesUtil.amountAfterCharge(5.5);
		
		assertEquals(5.5*Fee.PERCENT_REMAINING_AFTER_FEE,chargedAmount);
	}
	
	@Test
	public void amountAfterChargeWithNullAmount() throws NegativeAmountException{
		assertThrows(NullPointerException.class,() -> ChargeFeesUtil.amountAfterCharge(null));
	}

	@Test
	public void amountAfterChargeWithNegativeAmount() throws NegativeAmountException{
		assertThrows(NegativeAmountException.class, () -> ChargeFeesUtil.amountAfterCharge(-5.5));
	}
	
	@Test
	public void companyFeeTest() throws NegativeAmountException {
		Double companyFee = ChargeFeesUtil.companyFee((5.5));
		
		assertEquals(5.5*Fee.PERCENT_FEE_CHARGED,companyFee);
	}
	
	@Test
	public void companyFeeWithNullAmount() throws NegativeAmountException{
		assertThrows(NullPointerException.class, () -> ChargeFeesUtil.companyFee(null));
	}
	
	@Test
	public void companyFeeWithNegativeAmount() throws NegativeAmountException{
		assertThrows(NegativeAmountException.class, () -> ChargeFeesUtil.companyFee(-5.5));
	}
	
	@Test
	public void sumCompanyFeesTest() {
		Payment p1 = new Payment(null, null, null, null, null, null, 5.5);
		Payment p2 = new Payment(null, null, null, null, null, null, 5.5);
		Payment p3 = new Payment(null, null, null, null, null, null, 5.5);
		List<Payment> payments = new ArrayList<Payment>();
		payments.add(p1);payments.add(p2);payments.add(p3);
		
		Double sumFees = ChargeFeesUtil.sumCompanyFees(payments);
		
		assertEquals(3*5.5,sumFees);
	}
}
