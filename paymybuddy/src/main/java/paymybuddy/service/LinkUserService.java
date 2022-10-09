package paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paymybuddy.model.LinkUser;
import paymybuddy.repository.LinkUserRepository;

@Service
public class LinkUserService {
	 
	@Autowired
	LinkUserRepository repo;
	
	
	public boolean isExistingLinkUser(Integer accountId, Integer friendId) {
		return repo.existsByAccountIdAndFriendId(accountId, friendId);
	}
	
	public void saveLinkUser(LinkUser linkUser) {
		repo.save(linkUser);
	}
	
	public LinkUser getLinkUser(Integer id){
		Optional<LinkUser> opt = repo.findById(id); 
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	public Iterable<LinkUser> getLinkUsersWithAccountId(Integer accountId){
		return repo.findByAccountId(accountId);
	}
	
	public Iterable<LinkUser> getLinkUsersWithFriendId(Integer friendId){
		return repo.findByFriendId(friendId);
	}
	
	public void deleteLinkUser(LinkUser linkUser) {
		repo.delete(linkUser);
	}
	
	public Iterable<LinkUser> getAllLinkUsers(){
		return repo.findAll();
	}
}
