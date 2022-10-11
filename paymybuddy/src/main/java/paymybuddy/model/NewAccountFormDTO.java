package paymybuddy.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class NewAccountFormDTO {
	
	@Email(message="Please enter a valid email.")
	String email;
	
	@Size(min=12, message="The password must be over 12 characters long.")
	@NotBlank(message="Please enter a password")
	String password;
	
	@Size(max=64)
	@NotBlank(message="Please enter your firstname")
	String firstname;
	
	@Size(max=64)
	@NotBlank(message="Please enter your lastname")
	String lastname;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
