package com.paymybuddy.exception;

public class InsufficientBalanceException extends Exception{
	
	public InsufficientBalanceException(String errorMessage) {
		super(errorMessage);
	}

}
