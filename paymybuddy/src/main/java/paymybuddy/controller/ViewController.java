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

import paymybuddy.model.Payment;
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
    public String viewUserPage() {
    	return "index";
    }
    
    @GetMapping("/registration")
    public String viewRegistration() {
    	return "registration";
    }
    
}
