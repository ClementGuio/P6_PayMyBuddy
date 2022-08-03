package com.paymybuddy.test.unit;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.paymybuddy.model.LinkUser;
import com.paymybuddy.repository.LinkUserRepository;
import com.paymybuddy.repository.PaymentRepository;
import com.paymybuddy.service.LinkUserService;

@ExtendWith(MockitoExtension.class)
public class LinkUserServiceTest {

	@Mock
	LinkUserRepository repo;
	
	@InjectMocks
	LinkUserService service;
	
	@Test
	public void getAllLinkUsersTest() {
		LinkUser l1 = new LinkUser(1,1,2);
		LinkUser l2 = new LinkUser(2,1,3);
		List<LinkUser> linkUsers = new ArrayList<LinkUser>();
		linkUsers.add(l1);
		linkUsers.add(l2);
		
		when(repo.findAll()).thenReturn(linkUsers);
		
		Iterable<LinkUser> found = service.getAllLinkUsers();
		assertThat(found).hasSize(2);
	}
	
	@Test
	public void getLinkUserTest() {
		LinkUser l1 = new LinkUser(1,1,2);
		when(repo.findById(1)).thenReturn(Optional.of(l1));
		
		Optional<LinkUser> opt = service.getLinkUser(1);
		assertThat(opt).isPresent();
		assertEquals(1,opt.get().getAccountId());
	}
	
	
}
