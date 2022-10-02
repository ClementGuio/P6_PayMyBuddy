package paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import paymybuddy.exception.InsufficientBalanceException;
import paymybuddy.exception.NegativeAmountException;
import paymybuddy.model.Account;
import paymybuddy.model.DepositAndWithdrawFormDTO;
import paymybuddy.model.LinkUser;
import paymybuddy.model.LinkUserFormDTO;
import paymybuddy.model.NewAccountFormDTO;
import paymybuddy.model.Payment;
import paymybuddy.model.PaymentFormDTO;
import paymybuddy.service.AccountService;
import paymybuddy.service.LinkUserService;
import paymybuddy.service.PaymentManager;
import paymybuddy.service.PaymentService;

@Controller
//@ControllerAdvice
//TODO: rename class
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
	
	//TODO: donner la possibilité de déposer de l'argent
	
	//TODO: supprimer?
	@GetMapping(value = "/account")
	public Account userAccount(Authentication auth) {
		
		return accountService.getAccountWithEmail(auth.getName());	
	}
	//TODO : supprimer ?
	@GetMapping(value = "/user/name", params = {"accountId"})
	public String nameOf(@RequestParam Integer accountId) {
		Account acc =  accountService.getAccount(accountId);
		return acc.getFirstname()+" "+acc.getLastname();
	}
	
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
		
		Account acc = new Account(null, accountForm.getEmail(), accountForm.getPassword(), Double.valueOf(0), accountForm.getFirstname(), accountForm.getLastname());
		logger.info("created account : "+acc);
		accountService.saveOrUpdateAccount(acc);
		
		
		return "redirect:/login";
	}
	
	@PostMapping(value="/user/addconnection")
	public String addLinkUser(@Valid @ModelAttribute("linkUserForm") LinkUserFormDTO linkUserForm, 
							BindingResult bindingResult, Authentication auth) {
		logger.info("add linkUser");
		
		if (bindingResult.hasErrors()) {
			return "index";
		}
		
		String emailLinked = linkUserForm.getEmail();
		
		if (!accountService.isExistingEmail(emailLinked)) {
			bindingResult.rejectValue("email", "", "This email doesn't exist");
			return "index";
		}
		
		Account userAccount = accountService.getAccountWithEmail(auth.getName());
		Account accToLink = accountService.getAccountWithEmail(emailLinked);
		
		if (linkUserService.isExistingLinkUser(userAccount.getUserId(), accToLink.getUserId())) {
			bindingResult.rejectValue("email","", "This connection already exists.");
			return "index";
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
			
			return "payment";
		}
		
		if (!accountService.isExistingEmail(paymentForm.getCreditorEmail())) {
			
			bindingResult.rejectValue("creditorEmail", "", "This email isn't registered.");
			return "payment";
		}
		
		Account debitor = accountService.getAccountWithEmail(auth.getName());
		Account creditor = accountService.getAccountWithEmail(paymentForm.getCreditorEmail());
		logger.info("createPayment : debitor: "+debitor.getEmail()+"\ncreditor: "+creditor.getEmail()+
				"\ndescription: "+paymentForm.getDescription()+"\namount: "+paymentForm.getAmount());
		paymentManager.paySomeone(debitor, creditor, paymentForm.getDescription(), paymentForm.getAmount());
		
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
	
	//TODO: DTO
	@PostMapping(value="/deposit", params= {"amount"})
	public String depositMoney(@RequestParam Double amount, Authentication auth) {
		logger.info("deposit money");
		Account acc = accountService.getAccountWithEmail(auth.getName());
		acc.addMoney(amount);
		accountService.saveOrUpdateAccount(acc);
		
		return "redirect:/index";
	}
	@PostMapping(value="/withdraw", params= {"amount"})
	public String withdrawMoney(@RequestParam Double amount, /*BindingResult bindingResult,*/ Authentication auth) throws InsufficientBalanceException{
		logger.info("withdraw money");
		Account acc = accountService.getAccountWithEmail(auth.getName());
		//try {
			acc.withdrawMoney(amount);
		//}catch (InsufficientBalanceException e) {
		//	bindingResult.rejectValue("amount", "", e.getMessage());
		//}
		accountService.saveOrUpdateAccount(acc);
		
		return "redirect:/index";
	}

}
