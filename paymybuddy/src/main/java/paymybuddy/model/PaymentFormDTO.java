package paymybuddy.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class PaymentFormDTO {

	@Email(message="Please enter a valid email.")
	@NotBlank(message="Please choose an account.")
	String creditorEmail;
	
	@Size(max=50, message="The size of the description must not exceed 50 characters.")
	@NotBlank(message="Please enter a description.")
	String description;
	
	@Min(value=1, message="Choose an amount greater than 1 euro.")
	@Positive(message="The amount must be positive.")
	@NotNull(message="Please choose an amount.")
	Double amount;

	public String getCreditorEmail() {
		return creditorEmail;
	}

	public void setCreditorEmail(String creditorEmail) {
		this.creditorEmail = creditorEmail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
