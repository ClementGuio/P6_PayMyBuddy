package com.paymybuddy.test.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.model.Account;
import com.paymybuddy.model.LinkUser;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.LinkUserService;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class LinkUserServiceTest {
	
	@Autowired
	LinkUserService service;
	
	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveTest() {
		LinkUser l1 = new LinkUser(null, 1000001, 1000002);
		service.saveLinkUser(l1);
		
		assertThat(l1.getLinkId()).isGreaterThan(1000000);
	}

	@Test
	@Order(2)
	public void getLinkUserTest() {
		LinkUser l1 = service.getLinkUser(1000001);
		
		assertThat(l1).isNotNull();
	}
	
	@Test
	@Order(3)
	public void getLinkUsersWithAccountIdTest() {
		Iterable<LinkUser> links = service.getLinkUsersWithAccountId(1000003);
		LinkUser l1 = new LinkUser(null, 1000003, 1000001);
		LinkUser l2 = new LinkUser(null, 1000003, 1000002);
		
		assertThat(links).contains(l1).contains(l2);
	}
	
	@Test
	@Order(4)
	public void getLinkUsersWithFriendIdTest() {
		Iterable<LinkUser> links = service.getLinkUsersWithFriendId(1000001);
		LinkUser l1 = new LinkUser(null, 1000002, 1000001);
		LinkUser l2 = new LinkUser(null, 1000003, 1000001);
		
		assertThat(links).contains(l1).contains(l2);
	}
	
	@Test
	@Order(5)
	public void getAllLinkUsersTest() {
		assertThat(service.getAllLinkUsers()).hasSize(4);
	}
	
	@Test
	@Order(6)
	public void deleteLinkUserTest() {
		LinkUser l1 = new LinkUser(1000001,1000001,1000002);
		
		assertThat(service.getLinkUsersWithAccountId(1000001)).contains(l1);
		service.deleteLinkUser(l1);
		assertThat(service.getLinkUsersWithAccountId(1000001)).doesNotContain(l1);
	}
	
}
