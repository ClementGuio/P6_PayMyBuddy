package com.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.LinkUser;
import com.paymybuddy.repository.LinkUserRepository;

@Service
public class LinkUserService {
	
	@Autowired
	LinkUserRepository repo;
	
	public Iterable<LinkUser> getAllLinkUsers(){
		return repo.findAll();
	}
	
	public Optional<LinkUser> getLinkUser(Integer id){
		return repo.findById(id);
	}
	
	//ajouter un ami
	public void addLinkUser(LinkUser linkUser) {
		repo.save(linkUser);
	}
	
	//Liste des amis
	public Iterable<LinkUser> getLinkUsersWithAccountId(Integer accountId){
		return repo.findByAccountId(accountId);
	}
	
	public Iterable<LinkUser> getLinkUsersWithFriendId(Integer friendId){
		return repo.findByFriendId(friendId);
	}
	
	//Supprimer un ami
	public void removeLinkUser(LinkUser linkUser) {
		repo.delete(linkUser);
	}

}
