package com.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.Payment;
import com.paymybuddy.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepository repo;
	
	public Iterable<Payment> getAllPayments(){
		return repo.findAll();
	}
	
	public Optional<Payment> getPayment(Integer id){
		return repo.findById(id);
	}
	
	public void saveOrUpdatePayment(Payment payment) {
		repo.save(payment);
	}
	
	public Iterable<Payment> getPaymentWithCreditorId(Integer creditorId){
		return repo.findByCreditorId(creditorId);
	}
	
	//Liste des paiements
	public Iterable<Payment> getPaymentWithDebitorId(Integer debitorId){
		return repo.findByDebitorId(debitorId);
	}

}
