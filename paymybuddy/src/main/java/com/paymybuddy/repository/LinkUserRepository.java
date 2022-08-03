package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.LinkUser;

@Repository
public interface LinkUserRepository extends CrudRepository<LinkUser,Integer>{

	Iterable<LinkUser> findByAccountId(Integer accountId);
	
	Iterable<LinkUser> findByFriendId(Integer friendId);
}
