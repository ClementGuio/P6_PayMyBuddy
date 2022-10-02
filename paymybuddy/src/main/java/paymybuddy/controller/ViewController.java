package paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import paymybuddy.model.DepositAndWithdrawFormDTO;
import paymybuddy.model.LinkUserFormDTO;
import paymybuddy.model.NewAccountFormDTO;
import paymybuddy.model.Payment;
import paymybuddy.model.PaymentFormDTO;
import paymybuddy.service.AccountService;
import paymybuddy.service.PaymentService;

@Controller
//@ControllerAdvice
public class ViewController {
	
	Logger logger = LoggerFactory.getLogger(ViewController.class);

	@Autowired
	AccountService accountService;
	
	@Autowired
	PaymentService paymentService;
	
    @GetMapping("/login")
    public String viewLoginPage() {     
        return "login";
    }
    
    @GetMapping("/index")
    public String viewUserPage(Model model) {
    	logger.info("View loads index.html");
    	model.addAttribute("linkUserForm", new LinkUserFormDTO());
    	return "index";
    }
    
    @GetMapping("/registration")
    public String viewRegistration(Model model) {
    	logger.info("View loads registration.html");
    	model.addAttribute("accountForm", new NewAccountFormDTO());
    	
    	return "registration";
    }
    
    @GetMapping("/payment")
    public String viewPayment(Model model) {
    	logger.info("View loads payment.html");
    	model.addAttribute("paymentForm", new PaymentFormDTO());
    	return "payment";
    }
    
    @GetMapping("/depositOrWithdraw")
    public String viewWithdrawOrDeposit(Model model) {
    	logger.info("View loads withdrawOrDeposit.html");
    	model.addAttribute("depositOrWithdrawForm", new DepositAndWithdrawFormDTO());
    	return "depositOrWithdraw";
    }
}
