package paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paymybuddy.model.Account;
import paymybuddy.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository repo;
	
	/**
	 * Save an account or update it if already exists.
	 * @param account
	 * @return saved account
	 */
	public Account saveOrUpdateAccount(Account account) {
		return repo.save(account); 
	}
	
	/**
	 * Get the account with this email.
	 * @param email
	 * @return account with this email, or null if this email isn't registered.
	 */
	public Account getAccountWithEmail(String email) {
		Optional<Account> opt = repo.findByEmail(email);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	} 
	
	/**
	 * Delete the account with this email
	 * @param email
	 */
	public void deleteAccount(String email) {
		repo.deleteByEmail(email);
	}

	
	//TODO : supprimer ?
	/**
	 * Get all accounts registered.
	 * @return all accounts registered
	 */
	public Iterable<Account> getAllAccounts(){
		return repo.findAll();
	}
	
	/**
	 * Get account with thid id.
	 * @param Id
	 * @return id's account
	 */
	public Account getAccount(Integer Id){
		Optional<Account> opt = repo.findById(Id);
		if (opt.isPresent()) {
			return opt.get();
		}else {
			//throw new SQLException("There is no account with this id.");
			return null;
		}
	}
	
	// TODO : supprimer ?
	public String getNameOf(Integer accountId) {
		Account account = getAccount(accountId);
		if (account != null){
			return account.getFirstname()+" "+account.getLastname();
		}
		return null;
	}
	
	//TODO: inutile ?
	/*
	public Iterable<Account> getAccountsWithFirstnameAndLastname(String firstname, String lastname){
		return repo.findByFirstnameAndLastname(firstname, lastname);
	}
	*/
}
