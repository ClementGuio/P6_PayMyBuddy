package com.paymybuddy.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import paymybuddy.model.LinkUser;
import paymybuddy.service.LinkUserService;

@DataJpaTest
@ContextConfiguration(classes=paymybuddy.PaymybuddyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class LinkUserServiceTest {
	
	@Autowired
	LinkUserService service;
	
	@Test
	public void saveTest() {
		LinkUser l1 = new LinkUser(null, 1000001, 1000002);
		service.saveLinkUser(l1);
		
		assertThat(l1.getLinkId()).isGreaterThan(1000000);
	}

	@Test
	public void getLinkUserTest() {
		LinkUser l1 = service.getLinkUser(1000001);
		
		assertThat(l1).isNotNull();
	}
	
	@Test
	public void getLinkUsersWithAccountIdTest() {
		Iterable<LinkUser> links = service.getLinkUsersWithAccountId(1000003);
		LinkUser l1 = new LinkUser(null, 1000003, 1000001);
		LinkUser l2 = new LinkUser(null, 1000003, 1000002);
		
		assertThat(links).contains(l1).contains(l2);
	}
	
	@Test
	public void getLinkUsersWithFriendIdTest() {
		Iterable<LinkUser> links = service.getLinkUsersWithFriendId(1000001);
		LinkUser l1 = new LinkUser(null, 1000002, 1000001);
		LinkUser l2 = new LinkUser(null, 1000003, 1000001);
		
		assertThat(links).contains(l1).contains(l2);
	}
	
	@Test
	public void getAllLinkUsersTest() {
		assertThat(service.getAllLinkUsers()).hasSizeGreaterThanOrEqualTo(3);
	}
	
	@Test
	public void deleteLinkUserTest() {
		LinkUser l1 = new LinkUser(1000001,1000001,1000002);
		
		assertThat(service.getLinkUsersWithAccountId(1000001)).contains(l1);
		service.deleteLinkUser(l1);
		assertThat(service.getLinkUsersWithAccountId(1000001)).doesNotContain(l1);
	}
	
}
