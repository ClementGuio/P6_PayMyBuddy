package paymybuddy.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LinkUserFormDTO {
	
	@Email(message="Please enter a valid email.")
	@NotBlank(message="Please enter a valid email.")
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
