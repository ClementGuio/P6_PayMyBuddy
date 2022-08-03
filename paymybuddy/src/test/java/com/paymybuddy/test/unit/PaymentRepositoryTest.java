package com.paymybuddy.test.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.model.LinkUser;
import com.paymybuddy.model.Payment;
import com.paymybuddy.repository.LinkUserRepository;
import com.paymybuddy.repository.PaymentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class PaymentRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	PaymentRepository repo;
	
	
	//TODO : créer données de test cohérentes
	@Test
	public void findAllTest() {
		Payment a1 = new Payment(1000001,1000001,1000002,LocalDateTime.of(2020, 1, 1, 1, 0),null,Float.valueOf(5));
		Payment a2 = new Payment(1000002,1000002,1000001,LocalDateTime.of(2020, 1, 1, 1, 0),null,Float.valueOf(5));
		
		Iterable<Payment> transactions = repo.findAll();
		assertThat(transactions).contains(a1,a2);
	}
	
	@Test
	public void findByIdTest() {
		Optional<Payment> opt = repo.findById(1000001);
		
		assertThat(opt).isPresent();
	}
	
	@Test
	public void findByIdUnknownTest() {
		Optional<Payment> opt = repo.findById(1000000);
		
		assertThat(opt).isEmpty();
	}
	
	@Test
	public void findByCreditorIdTest() {
		Iterable<Payment> found = repo.findByCreditorId(1000001);
		
		assertThat(found).hasSize(1);
	}
	
	@Test
	public void findByDebitorIdTest() {
		Iterable<Payment> found = repo.findByDebitorId(1000001);
		
		assertThat(found).hasSize(1);
	}

}