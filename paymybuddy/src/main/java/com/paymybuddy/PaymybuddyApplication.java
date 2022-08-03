package com.paymybuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.paymybuddy.model.Account;
import com.paymybuddy.service.AccountService;

@SpringBootApplication
@Controller
@ComponentScan(basePackages = {"com.paymybuddy"}) //Resolved NoSuchBeanDefinitionException during test
public class PaymybuddyApplication implements CommandLineRunner{
	
	@Autowired 
	AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		Iterable<Account> users = accountService.getAllAccounts();
		users.forEach(user -> System.out.println(user.getEmail()));
	}
}

//Page Web
//TODO : login 
//TODO : liste transaction 
//TODO : liste amis
//TODO : ajouter ami
//TODO : choisir montant et enregistrer transaction (+update des soldes)
