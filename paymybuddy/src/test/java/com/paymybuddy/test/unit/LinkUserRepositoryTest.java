package com.paymybuddy.test.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.model.Account;
import com.paymybuddy.model.LinkUser;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.LinkUserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class LinkUserRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	LinkUserRepository repo;
	
	
	//TODO : créer données de test cohérentes
	@Test
	public void findAllTest() {
		LinkUser a1 = new LinkUser(1000001,1000001,1000002);
		LinkUser a2 = new LinkUser(1000002,1000002,1000001);
		
		Iterable<LinkUser> linkUsers = repo.findAll();
		assertThat(linkUsers).contains(a1,a2);
	}
	
	@Test
	public void findByIdTest() {
		Optional<LinkUser> opt = repo.findById(1000001);
		
		assertThat(opt).isPresent();
	}
	
	@Test
	public void findByIdUnknownTest() {
		Optional<LinkUser> opt = repo.findById(1000000);
		
		assertThat(opt).isEmpty();
	}
	
	@Test
	public void findByAccountIdTest() {
		Iterable<LinkUser> found = repo.findByAccountId(1000001);
		
		assertThat(found).hasSize(1);
	}
	
	@Test
	public void findByFriendIdTest() {
		Iterable<LinkUser> found = repo.findByFriendId(1000002);
		
		assertThat(found).hasSize(1);
	}

}
