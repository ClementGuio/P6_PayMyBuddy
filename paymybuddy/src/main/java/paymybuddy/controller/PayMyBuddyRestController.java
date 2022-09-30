package paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import paymybuddy.exception.InsufficientBalanceException;
import paymybuddy.exception.NegativeAmountException;
import paymybuddy.model.Account;
import paymybuddy.model.LinkUser;
import paymybuddy.model.Payment;
import paymybuddy.service.AccountService;
import paymybuddy.service.LinkUserService;
import paymybuddy.service.PaymentManager;
import paymybuddy.service.PaymentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Controller
public class PayMyBuddyRestController {
	
	Logger logger = LoggerFactory.getLogger(PayMyBuddyRestController.class);
	
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	
	@Autowired
	LinkUserService linkUserService;

	@Autowired
	AccountService accountService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PaymentManager paymentManager;
	
	// TODO : supprimer ?
	@GetMapping(value = "/user/account", params = {"idAccount"})
	public Account accountWithId(@RequestParam Integer idAccount) {
		return accountService.getAccount(idAccount);	
	}
	//TODO : supprimer ?
	@GetMapping(value = "/user/name", params = {"accountId"})
	public String nameOf(@RequestParam Integer accountId) {
		Account acc =  accountService.getAccount(accountId);
		return acc.getFirstname()+" "+acc.getLastname();
	}
	
	//TODO : s'assurer qu'on ne puisse pas ajouter 2 fois le même lien
	@PostMapping(value="/user/addconnection", params = {"email"})
	public void addLinkUser(@RequestParam String email, Authentication auth) {
		logger.info("addLinkUser("+auth.getName()+", "+email+")");
		Account userAccount = accountService.getAccountWithEmail(auth.getName());
		Account accToLink = accountService.getAccountWithEmail(email);
		LinkUser link = new LinkUser(null,userAccount.getUserId(),accToLink.getUserId());
		logger.info("new LinkUser : "+link);
		linkUserService.saveLinkUser(link);
	}
	
	@PostMapping(value="/newAccount", params = {"email","password","firstname","lastname"})
	public void createNewAccount(@RequestParam String email,@RequestParam String password,@RequestParam String firstname,@RequestParam String lastname) {
		logger.info("create new account");
		Account acc = new Account(null,email,password,Double.valueOf(0),firstname,lastname);
		logger.info("new account : "+acc);
		accountService.saveOrUpdateAccount(acc);
	}
	
	@PostMapping(value="/executePayment", params= {"creditorEmail","description","amount"})
	public void createPayment(Authentication auth, @RequestParam String creditorEmail,
							@RequestParam String description, @RequestParam Double amount) 
							throws NegativeAmountException, InsufficientBalanceException{
		logger.info("createPayment is called");
		Account debitor = accountService.getAccountWithEmail(auth.getName());
		Account creditor = accountService.getAccountWithEmail(creditorEmail);
		logger.info("createPayment : debitor: "+debitor.getEmail()+"\ncreditor: "+creditor.getEmail()+
				"\ndescription: "+description+"\namount: "+amount);
		paymentManager.paySomeone(debitor, creditor, description, amount);
	}
	

}
