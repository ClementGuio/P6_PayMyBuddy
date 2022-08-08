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
	
	/**
	 * Save a Payment or update it if already exists.
	 * @param payment
	 */
	public void saveOrUpdatePayment(Payment payment) {
		repo.save(payment);
	}
	
	/**
	 * Get Payment with this id.
	 * @param id
	 * @return Payment with this id, ot null if id isn't registered.
	 */
	public Payment getPayment(Integer id){
		Optional<Payment> opt = repo.findById(id); 
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	/**
	 * Get all payments with this debitor's id.
	 * @param debitorId
	 * @return all payments with debitorId
	 */
	public Iterable<Payment> getPaymentsWithDebitorId(Integer debitorId){
		return repo.findByDebitorId(debitorId);
	}
	
	/**
	 * Get all payments with this creditor's id.
	 * @param creditorId
	 * @return all payments with creditorId
	 */
	public Iterable<Payment> getPaymentsWithCreditorId(Integer creditorId){
		return repo.findByCreditorId(creditorId);
	}
	
	/**
	 * Delete this payment.
	 * @param payment
	 */
	public void deletePayment(Payment payment) {
		repo.delete(payment);
	}
	
	//TODO : inutile
	public Iterable<Payment> getAllPayments(){
		return repo.findAll();
	}

}
