package com.paymybuddy.paymybuddy.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.Optional;

import paymybuddy.model.Account;
import paymybuddy.repository.AccountRepository;

@DataJpaTest
@ContextConfiguration(classes=paymybuddy.PaymybuddyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class AccountRepositoryTest {
/*
	@Autowired
	TestEntityManager entityManager;
*/	
	@Autowired
	AccountRepository repo;
	
	@Test
	public void findAllTest() {
		
		Iterable<Account> accounts = repo.findAll();
		assertThat(accounts).hasSizeGreaterThanOrEqualTo(3);
	}
	
	@Test
	public void findByIdTest() {
		Optional<Account> opt = repo.findById(1000001);
		
		assertThat(opt).isPresent();
	}
	
	@Test
	public void findByIdUnknownTest() {
		Optional<Account> opt = repo.findById(1000000);
		
		assertThat(opt).isEmpty();
	}
	
	@Test
	public void findByEmailTest() {
		Optional<Account> opt = repo.findByEmail("one@email.com");
		
		assertThat(opt).isPresent();
		assertEquals("one@email.com",opt.get().getEmail());
	}
	
	@Test
	public void findByFirstnameAndLastnameTest() {
		Iterable<Account> found = repo.findByFirstnameAndLastname("firstname", "lastname");
		
		assertThat(found).hasSize(3);
	}
	/*
	@Test
	public void saveAccountTest() {
		Account a1 = new Account(1000003,"three@email.com","motdepasse",Double.valueOf(0),null,null);
		
		repo.save(a1);

		Optional<Account> opt = repo.findById(1000003);
		Account found = opt.get();
		assertEquals(1000003,found.getUserId());
		//assertThat(opt).isPresent();
	}

	@Test
	public void saveAndDeleteTest() {
		Account a1 = new Account(1000004,"four@email.com","motdepasse",Double.valueOf(0),null,null);
		
		repo.save(a1);
		Optional<Account> opt = repo.findById(1000004);
		
		assertThat(opt).isPresent();
		repo.delete(a1);
		opt = repo.findById(1000004);
		assertThat(opt).isEmpty();
	}
	*/
}
