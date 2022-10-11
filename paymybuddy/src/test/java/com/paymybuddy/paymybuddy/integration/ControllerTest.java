package com.paymybuddy.paymybuddy.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

 import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import paymybuddy.controller.ModelController;
import paymybuddy.model.DepositAndWithdrawFormDTO;
import paymybuddy.model.LinkUserFormDTO;
import paymybuddy.model.NewAccountFormDTO;
import paymybuddy.model.PaymentFormDTO;
import paymybuddy.service.AccountService;
import paymybuddy.service.LinkUserService;
import paymybuddy.service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration(classes=paymybuddy.PaymybuddyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "../resources/setup_testDatabase.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "../resources/cleanup_testDatabase.sql", executionPhase = AFTER_TEST_METHOD)
public class ControllerTest {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	LinkUserService linkUserService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	ModelController modelController;
	
	@Mock
	Authentication auth;
	
	@Autowired
	MockMvc mockMvc;
	
	Model model;

	
	// Post forms
	@Test
	@WithMockUser(username="testone@email.com")
	public void postConnectionFormTest() throws Exception {
		this.mockMvc.perform(post("/addconnection")
			.param("email", "testthree@email.com")
			.sessionAttr("linkUserForm", new LinkUserFormDTO())
			.with(csrf()))
			.andExpect(status().isFound());
		
		Integer id = accountService.getAccountWithEmail("testone@email.com").getUserId();
		assertThat(linkUserService.getLinkUsersWithAccountId(id)).hasSize(2);
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postConnectionFormWithBadEmailTest() throws Exception {
		this.mockMvc.perform(post("/addconnection")
			.param("email", "bad")
			.sessionAttr("linkUserForm", new LinkUserFormDTO())
			.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors());
		
		Integer id = accountService.getAccountWithEmail("testone@email.com").getUserId();
		assertThat(linkUserService.getLinkUsersWithAccountId(id)).hasSize(1);
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postConnectionFormWithBlankEmailTest() throws Exception {
		this.mockMvc.perform(post("/addconnection")
			.param("email", "")
			.sessionAttr("linkUserForm", new LinkUserFormDTO())
			.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors());
		
		Integer id = accountService.getAccountWithEmail("testone@email.com").getUserId();
		assertThat(linkUserService.getLinkUsersWithAccountId(id)).hasSize(1);
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postConnectionFormAlreadyLinkedTest() throws Exception {
		this.mockMvc.perform(post("/addconnection")
			.param("email", "testtwo@email.com")
			.sessionAttr("linkUserForm", new LinkUserFormDTO())
			.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors());
		
		Integer id = accountService.getAccountWithEmail("testone@email.com").getUserId();
		assertThat(linkUserService.getLinkUsersWithAccountId(id)).hasSize(1);
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postConnectionFormUnknownEmailTest() throws Exception {
		this.mockMvc.perform(post("/addconnection")
			.param("email", "unknown@email.com")
			.sessionAttr("linkUserForm", new LinkUserFormDTO())
			.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors());
		
		Integer id = accountService.getAccountWithEmail("testone@email.com").getUserId();
		assertThat(linkUserService.getLinkUsersWithAccountId(id)).hasSize(1);
	}
	
	
	@Test
	public void postNewAccountFormTest() throws Exception{
		this.mockMvc.perform(post("/newAccount")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("email", "newaccount@email.com")
			        .param("firstname", "new")
			        .param("lastname", "account")
			        .param("password", "password123456")
			        .sessionAttr("accountForm",new NewAccountFormDTO())
					.with(csrf()))
			        .andExpect(status().isFound());
		
		assertThat(accountService.getAccountWithEmail("newaccount@email.com")).isNotNull();
	}
	
	@Test
	public void postNewAccountFormWithBadEmailTest() throws Exception{
		this.mockMvc.perform(post("/newAccount")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("email", "bad")
			        .param("firstname", "new")
			        .param("lastname", "account")
			        .param("password", "password123456")
			        .sessionAttr("accountForm",new NewAccountFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
		
		assertThat(accountService.getAccountWithEmail("bad")).isNull();
	}
	
	@Test
	public void postNewAccountFormWithBlankFirstnameTest() throws Exception{
		this.mockMvc.perform(post("/newAccount")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("email", "newaccount@email.com")
			        .param("firstname", "")
			        .param("lastname", "blank")
			        .param("password", "password123456")
			        .sessionAttr("accountForm",new NewAccountFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
		
		assertThat(accountService.getAccountWithEmail("newaccount@email.com")).isNull();
	}
	
	@Test
	public void postNewAccountFormWithBlankLastnameTest() throws Exception{
		this.mockMvc.perform(post("/newAccount")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("email", "newaccount@email.com")
			        .param("firstname", "blank")
			        .param("lastname", "")
			        .param("password", "password123456")
			        .sessionAttr("accountForm",new NewAccountFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
		
		assertThat(accountService.getAccountWithEmail("newaccount@email.com")).isNull();
	}
	
	@Test
	public void postNewAccountFormWithBadPasswordTest() throws Exception{
		this.mockMvc.perform(post("/newAccount")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("email", "newaccount@email.com")
			        .param("firstname", "new")
			        .param("lastname", "account")
			        .param("password", "bad")
			        .sessionAttr("accountForm",new NewAccountFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
		
		assertThat(accountService.getAccountWithEmail("newaccount@email.com")).isNull();
	}
	
	@Test
	public void postNewAccountFormWithBlankPasswordTest() throws Exception{
		this.mockMvc.perform(post("/newAccount")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("email", "newaccount@email.com")
			        .param("firstname", "new")
			        .param("lastname", "account")
			        .param("password", "")
			        .sessionAttr("accountForm",new NewAccountFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
		
		assertThat(accountService.getAccountWithEmail("newaccount@email.com")).isNull();
	}
	
	@Test
	public void postNewAccountFormWithEmailAlreadyRegisteredTest() throws Exception{
		this.mockMvc.perform(post("/newAccount")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("email", "testone@email.com")
			        .param("firstname", "new")
			        .param("lastname", "account")
			        .param("password", "password123456")
			        .sessionAttr("accountForm",new NewAccountFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postPaymentFormTest() throws Exception{
		
		this.mockMvc.perform(post("/executePayment")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("creditorEmail", "testtwo@email.com")
			        .param("description", "postPaymentTest")
			        .param("amount", "10")
			        .sessionAttr("paymentForm",new PaymentFormDTO())
					.with(csrf()))
			        .andExpect(status().isFound());
		
		Integer id = accountService.getAccountWithEmail("testone@email.com").getUserId();
		assertThat(paymentService.getAllPaymentsOf(id)).hasSize(4);
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postPaymentFormWithBadEmailTest() throws Exception{
		
		this.mockMvc.perform(post("/executePayment")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("creditorEmail", "bad")
			        .param("description", "postPaymentTest")
			        .param("amount", "10")
			        .sessionAttr("paymentForm",new PaymentFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postPaymentFormWithBlankDescriptionTest() throws Exception{
		
		this.mockMvc.perform(post("/executePayment")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("creditorEmail", "testtwo@email.com")
			        .param("description", "")
			        .param("amount", "10")
			        .sessionAttr("paymentForm",new PaymentFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postPaymentFormWithNullAmountTest() throws Exception{
		
		this.mockMvc.perform(post("/executePayment")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("creditorEmail", "testtwo@email.com")
			        .param("description", "postPaymentTest")
			        .param("amount", "")
			        .sessionAttr("paymentForm",new PaymentFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postPaymentFormWithUnknownEmailTest() throws Exception{
		
		this.mockMvc.perform(post("/executePayment")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("creditorEmail", "unknown@email.com")
			        .param("description", "postPaymentTest")
			        .param("amount", "10")
			        .sessionAttr("paymentForm",new PaymentFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser(username="testone@email.com")
	public void postPaymentFormWithTooBigAmountTest() throws Exception{
		
		this.mockMvc.perform(post("/executePayment")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("creditorEmail", "testtwo@email.com")
			        .param("description", "postPaymentTest")
			        .param("amount", "1000")
			        .sessionAttr("paymentForm",new PaymentFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser(username="testtwo@email.com")
	public void postDepositOrWithdrawFormTest() throws Exception{
		
		this.mockMvc.perform(post("/executeDepositOrWithdraw")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("action", "deposit")
			        .param("amount", "10")
			        .sessionAttr("depositOrWithdrawForm",new DepositAndWithdrawFormDTO())
					.with(csrf()))
			        .andExpect(status().isFound());
		
		assertEquals(110.0, accountService.getAccountWithEmail("testtwo@email.com").getBalance());
	}
	
	@Test
	@WithMockUser(username="testtwo@email.com")
	public void postDepositOrWithdrawFormWithNullAmountTest() throws Exception{
		
		this.mockMvc.perform(post("/executeDepositOrWithdraw")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("action", "deposit")
			        .param("amount", "")
			        .sessionAttr("depositOrWithdrawForm",new DepositAndWithdrawFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());
	}
	
	@Test
	@WithMockUser(username="testtwo@email.com")
	public void postDepositOrWithdrawFormWithTooMuchWithdrawTest() throws Exception{
		
		this.mockMvc.perform(post("/executeDepositOrWithdraw")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			        .param("action", "withdraw")
			        .param("amount", "1000")
			        .sessionAttr("depositOrWithdrawForm",new DepositAndWithdrawFormDTO())
					.with(csrf()))
			        .andExpect(status().isOk())
			        .andExpect(model().hasErrors());		
	}
	
	
	// Get models
	@Test
	public void getFriendsListTest() throws Exception {
		this.mockMvc.perform(get("/friends"))
        .andExpect(status().isFound());
	}
	
	@Test
	public void getAllPaymentsTest() throws Exception {
		this.mockMvc.perform(get("/payments"))
        .andExpect(status().isFound());
	}
	
	@Test
	public void getUserAccountTest() throws Exception {
		this.mockMvc.perform(get("/account"))
        .andExpect(status().isFound());
	}


	
	// Get views
	@Test
	@WithMockUser(username="testthree@email.com")
	public void getConnectionTest() throws Exception {
		mockMvc.perform(get("/connection"))
				.andExpect(status().isOk());
	}
	

	@Test
	@WithMockUser(username="testthree@email.com")
	public void getIndexTest() throws Exception{
		mockMvc.perform(get("/index"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	@WithMockUser(username="testthree@email.com")
	public void getDepositOrWithdrawTest() throws Exception {
		mockMvc.perform(get("/depositOrWithdraw"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getLoginTest() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getRegistrationTest() throws Exception {
		mockMvc.perform(get("/registration"))
				.andExpect(status().isOk());
	}

}
