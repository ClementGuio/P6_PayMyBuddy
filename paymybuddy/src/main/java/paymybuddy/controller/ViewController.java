package paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import paymybuddy.model.DepositAndWithdrawFormDTO;
import paymybuddy.model.LinkUserFormDTO;
import paymybuddy.model.NewAccountFormDTO;
import paymybuddy.model.PaymentFormDTO;

@Controller
public class ViewController {
	
	Logger logger = LoggerFactory.getLogger(ViewController.class);

    @GetMapping("/login")
    public String viewLoginPage() {     
        return "login";
    }
    
    @GetMapping("/index")
    public String viewUserPage(Model model) {
    	logger.info("View loads index.html");
    	model.addAttribute("paymentForm", new PaymentFormDTO());
    	return "index";
    }
    
    @GetMapping("/registration")
    public String viewRegistration(Model model) {
    	logger.info("View loads registration.html");
    	model.addAttribute("accountForm", new NewAccountFormDTO());
    	
    	return "registration";
    }
    
    @GetMapping("/connection")
    public String viewConnection(Model model) {
    	logger.info("View loads connection.html");
    	model.addAttribute("linkUserForm", new LinkUserFormDTO());
    	return "connection";
    }
    
    @GetMapping("/depositOrWithdraw")
    public String viewWithdrawOrDeposit(Model model) {
    	logger.info("View loads withdrawOrDeposit.html");
    	model.addAttribute("depositOrWithdrawForm", new DepositAndWithdrawFormDTO());
    	return "depositOrWithdraw";
    }
}
