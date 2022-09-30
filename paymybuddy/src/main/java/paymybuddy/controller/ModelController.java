package paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import paymybuddy.model.Account;
import paymybuddy.model.LinkUser;
import paymybuddy.service.AccountService;
import paymybuddy.service.LinkUserService;
import paymybuddy.service.PaymentService;

@ControllerAdvice
public class ModelController {

	Logger logger = LoggerFactory.getLogger(ModelController.class);

	@Autowired
	AccountService accountService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	LinkUserService linkUserService;

	@ModelAttribute
	public void friendsList(Model model, Authentication auth) {
		logger.info("friendList is called");
		if (auth!=null) {
			Integer userId = accountService.getAccountWithEmail(auth.getName()).getUserId();
			List<Account> friendList = new ArrayList<Account>();
			for (LinkUser link : linkUserService.getLinkUsersWithAccountId(userId)) {
				friendList.add(accountService.getAccount(link.getFriendId()));
			}
			model.addAttribute("friends",friendList);
		}
	}
	
	@ModelAttribute
	public void paymentsAsDebitor(Model model, Authentication auth){
		logger.info("paymentsAsDebitor is called");
    	if (auth!=null) {
    		logger.info("Load PaymentsAsDebitor : "+auth.getName());
    		Integer debitorId = accountService.getAccountWithEmail(auth.getName()).getUserId();
    		logger.info("paymentAsDebitor : "+debitorId);
    		model.addAttribute("payments",paymentService.getPaymentsWithDebitorId(debitorId));
    	}
	}
	
}
