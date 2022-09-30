package paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paymybuddy.model.LinkUser;
import paymybuddy.repository.LinkUserRepository;

@Service
public class LinkUserService {
	//TODO : ATTENTION -> ajouter un ami n'est pas réciproque 
	@Autowired
	LinkUserRepository repo;
	
	/**
	 * Save a linkUser.
	 * @param linkUser
	 */
	public void saveLinkUser(LinkUser linkUser) {
		repo.save(linkUser);
	}
	
	/**
	 * Get the linkUser with this id.
	 * @param id
	 * @return linkUser with id
	 */
	public LinkUser getLinkUser(Integer id){
		Optional<LinkUser> opt = repo.findById(id); 
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	/**
	 * Get linkUsers of the id's account.
	 * @param accountId
	 * @return all linkUsers with this accountId
	 */
	public Iterable<LinkUser> getLinkUsersWithAccountId(Integer accountId){
		return repo.findByAccountId(accountId);
	}
	
	//TODO : supprimer ?
	//Liste des personnes qui ont ajouté friendId comme ami (followers)
	/**
	 * Get linkUsers with id as friend.
	 * @param friendId
	 * @return all linkUsers with this friendId
	 */
	public Iterable<LinkUser> getLinkUsersWithFriendId(Integer friendId){
		return repo.findByFriendId(friendId);
	}
	
	/**
	 * Delete this linkUser.
	 * @param linkUser
	 */
	public void deleteLinkUser(LinkUser linkUser) {
		repo.delete(linkUser);
	}
	
	/**
	 * Get all linkUsers registered.
	 * @return all linkUsers registered
	 */
	public Iterable<LinkUser> getAllLinkUsers(){
		return repo.findAll();
	}
}
