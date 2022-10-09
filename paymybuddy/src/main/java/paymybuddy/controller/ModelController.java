package paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public void allPayments(Model model, Authentication auth){
		logger.info("payments is called");
    	if (auth!=null) {
    		logger.info("Load allPayments : "+auth.getName());
    		Integer accountId = accountService.getAccountWithEmail(auth.getName()).getUserId();
    		logger.info("allPayments : "+accountId);
    		model.addAttribute("payments",paymentService.getAllPaymentsOf(accountId));
    	}
	}
	
	@ModelAttribute
	public void userAccount(Model model, Authentication auth) {
		logger.info("userAccount is called");
		if (auth!=null) {
			Account acc = accountService.getAccountWithEmail(auth.getName());
			model.addAttribute("account", acc);
		}
	} 
	
}
