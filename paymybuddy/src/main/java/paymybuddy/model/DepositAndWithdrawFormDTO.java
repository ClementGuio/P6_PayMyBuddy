package paymybuddy.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DepositAndWithdrawFormDTO {

	@NotBlank(message="Please choose one option.")
	String action;
	
	@NotNull(message="Please choose an amount.")
	Double amount;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
