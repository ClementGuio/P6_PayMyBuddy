package paymybuddy.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import paymybuddy.exception.InsufficientBalanceException;
import paymybuddy.exception.NegativeAmountException;
import paymybuddy.model.Account;
import paymybuddy.model.DepositAndWithdrawFormDTO;
import paymybuddy.model.LinkUser;
import paymybuddy.model.LinkUserFormDTO;
import paymybuddy.model.NewAccountFormDTO;
import paymybuddy.model.PaymentFormDTO;
import paymybuddy.service.AccountService;
import paymybuddy.service.LinkUserService;
import paymybuddy.service.PaymentManager;
import paymybuddy.service.PaymentService;

@Controller
public class FormController {
	
	Logger logger = LoggerFactory.getLogger(FormController.class);
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	LinkUserService linkUserService;

	@Autowired
	AccountService accountService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PaymentManager paymentManager;
	
	@PostMapping(value="/newAccount")
	public String createNewAccount(@Valid @ModelAttribute("accountForm") NewAccountFormDTO accountForm, BindingResult bindingResult, Model model) {
		logger.info("create new account");
		
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		
		if (accountService.isExistingEmail(accountForm.getEmail())) {
			bindingResult.rejectValue("email", "", "This email is already registered");
			return "registration";
		}
		
		Account acc = new Account(null, accountForm.getEmail(), passwordEncoder.encode(accountForm.getPassword()), Double.valueOf(0), accountForm.getFirstname(), accountForm.getLastname());
		logger.info("created account : "+acc);
		accountService.saveOrUpdateAccount(acc);
		
		
		return "redirect:/login";
	}
	
	@PostMapping(value="/addconnection")
	public String addLinkUser(@Valid @ModelAttribute("linkUserForm") LinkUserFormDTO linkUserForm, 
							BindingResult bindingResult, Authentication auth) {
		logger.info("add linkUser");
		
		if (bindingResult.hasErrors()) {
			return "connection";
		}
		
		String emailLinked = linkUserForm.getEmail();
		
		if (!accountService.isExistingEmail(emailLinked)) {
			bindingResult.rejectValue("email", "", "This email doesn't exist");
			return "connection";
		}
		
		Account userAccount = accountService.getAccountWithEmail(auth.getName());
		Account accToLink = accountService.getAccountWithEmail(emailLinked);
		
		if (linkUserService.isExistingLinkUser(userAccount.getUserId(), accToLink.getUserId())) {
			bindingResult.rejectValue("email","", "This connection already exists.");
			return "connection";
		}
		
		LinkUser link = new LinkUser(null,userAccount.getUserId(),accToLink.getUserId());
		logger.info("new LinkUser : "+link);
		linkUserService.saveLinkUser(link);
		
		return "redirect:/index";
	}
	
	@PostMapping(value="/executePayment")
	public Object createPayment(@Valid @ModelAttribute("paymentForm") PaymentFormDTO paymentForm, 
								BindingResult bindingResult, Authentication auth) 
							throws NegativeAmountException, InsufficientBalanceException{
		logger.info("create a payment");
		
		if (bindingResult.hasErrors()) {
			
			return "index";
		}
		
		if (!accountService.isExistingEmail(paymentForm.getCreditorEmail())) {
			
			bindingResult.rejectValue("creditorEmail", "", "This email isn't registered.");
			return "index";
		}
		
		Account debitor = accountService.getAccountWithEmail(auth.getName());
		Account creditor = accountService.getAccountWithEmail(paymentForm.getCreditorEmail());
		logger.info("createPayment : debitor: "+debitor.getEmail()+"\ncreditor: "+creditor.getEmail()+
				"\ndescription: "+paymentForm.getDescription()+"\namount: "+paymentForm.getAmount());
		
		try {
			paymentManager.paySomeone(debitor, creditor, paymentForm.getDescription(), paymentForm.getAmount());
		}catch(InsufficientBalanceException e) {
			bindingResult.rejectValue("amount", "", e.getMessage());
			return "index";
		}
		
		return "redirect:/index";
	}
	
	@PostMapping("/executeDepositOrWithdraw")
	public String withdrawOrDepositMoney(@Valid @ModelAttribute("depositOrWithdrawForm") DepositAndWithdrawFormDTO depositOrWithdrawForm,
											BindingResult bindingResult, Model model, Authentication auth) {
		
		logger.info("deposit or withdraw money");
		
		if (bindingResult.hasErrors()) {
			return "depositOrWithdraw";
		}
		
		Account acc = accountService.getAccountWithEmail(auth.getName());
		
		String action = depositOrWithdrawForm.getAction();
		
		if (action.equals("withdraw")) {
			try {
				acc.withdrawMoney(depositOrWithdrawForm.getAmount());
			}catch (InsufficientBalanceException e) {
				bindingResult.rejectValue("amount", "", e.getMessage());
				return "depositOrWithdraw";
			}
		}
		if (action.equals("deposit")){
			acc.addMoney(depositOrWithdrawForm.getAmount());
		}
		
		accountService.saveOrUpdateAccount(acc);
		
		return "redirect:/index";
	}

}
