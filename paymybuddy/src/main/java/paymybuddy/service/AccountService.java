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

	
	public boolean isExistingEmail(String email) {
		return repo.existsByEmail(email);
	}

	public Account saveOrUpdateAccount(Account account) {
		return repo.save(account); 
	}
	
	public Account getAccountWithEmail(String email) {
		Optional<Account> opt = repo.findByEmail(email);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	} 
	
	public void deleteAccount(String email) {
		repo.deleteByEmail(email);
	}
	
	public Iterable<Account> getAllAccounts(){
		return repo.findAll();
	}
	
	public Account getAccount(Integer Id){
		Optional<Account> opt = repo.findById(Id);
		if (opt.isPresent()) {
			return opt.get();
		}else {
			return null;
		}
	}
	
	public String getNameOf(Integer accountId) {
		Account account = getAccount(accountId);
		if (account != null){
			return account.getFirstname()+" "+account.getLastname();
		}
		return null;
	}
	
}
