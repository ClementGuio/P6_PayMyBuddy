package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.LinkUser;
import com.paymybuddy.model.Payment;


@Repository
public interface PaymentRepository extends CrudRepository<Payment,Integer>{
	
	Iterable<Payment> findByCreditorId(Integer creditorId);
	
	Iterable<Payment> findByDebitorId(Integer debitorId);

}
