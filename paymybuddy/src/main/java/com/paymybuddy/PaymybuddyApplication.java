package com.paymybuddy;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.paymybuddy.model.Account;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.PaymentManager;

@SpringBootApplication
@Controller
@ComponentScan(basePackages = {"com.paymybuddy"}) //Resolved NoSuchBeanDefinitionException during test
public class PaymybuddyApplication implements CommandLineRunner{
	
	@Autowired 
	AccountService accountService;
	
	@Autowired
	PaymentManager manager;

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		/*
		Account a1 = new Account(null, "one@email.com", "pword", Double.valueOf(50), "firstname", "lastname");
		accountService.saveOrUpdateAccount(a1);
		Account a2 = accountService.getAccountWithEmail("one@email.com");
		System.out.println(a2);
		System.out.println("------------Before payment");
		Iterable<Account> accounts = accountService.getAllAccounts();
		accounts.forEach(account -> System.out.println("Id:"+account.getUserId()+", balance = "+account.getBalance()));
		a1 = accountService.getAccountWithEmail("three@email.com");
		Account a2 = accountService.getAccountWithEmail("prime@email.com");
		manager.paySomeone(a1, a2, "Test", Double.valueOf(3));
		a1 = null;
		a2 = null;
		System.out.println("----------------After payment");
		accounts = accountService.getAllAccounts();
		accounts.forEach(account -> System.out.println("Id:"+account.getUserId()+", balance = "+account.getBalance()));
		*/
	}
}

//TODO : ajouter annotations pour les entités et maybe @OneToMany etc...

//Page Web
//TODO : login 
//TODO : liste transaction 
//TODO : liste amis
//TODO : ajouter ami
//TODO : choisir montant et enregistrer transaction (+update des soldes)
