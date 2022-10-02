package paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import paymybuddy.model.Account;


@Repository
public interface AccountRepository extends CrudRepository<Account,Integer>{

	public Optional<Account> findByEmail(String email);
	
	//TODO: inutile
	public Iterable<Account> findByFirstnameAndLastname(String firstname, String lastname);
	
	public void deleteByEmail(String email);
	
	public boolean existsByEmail(String email);
}
