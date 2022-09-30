package paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paymybuddy.model.Payment;
import paymybuddy.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepository repo;
	
	@Autowired 
	AccountService accService;
	
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
			Payment payment = opt.get();
			payment.setCreditorName(accService.getNameOf(payment.getCreditorId()));
			payment.setDebitorName(accService.getNameOf(payment.getDebitorId()));
			return payment;
		}
		return null;
	}

	/**
	 * Get all payments with this debitor's id.
	 * @param debitorId
	 * @return all payments with debitorId
	 */
	public Iterable<Payment> getPaymentsWithDebitorId(Integer debitorId){
		Iterable<Payment> payments = repo.findByDebitorId(debitorId);
		for (Payment payment : payments) {
			payment.setCreditorName(accService.getNameOf(payment.getCreditorId()));
			payment.setDebitorName(accService.getNameOf(payment.getDebitorId()));
		}
		return payments;
	}
	
	/**
	 * Get all payments with this creditor's id.
	 * @param creditorId
	 * @return all payments with creditorId
	 */
	public Iterable<Payment> getPaymentsWithCreditorId(Integer creditorId){
		Iterable<Payment> payments = repo.findByCreditorId(creditorId);
		for (Payment payment : payments) {
			payment.setCreditorName(accService.getNameOf(payment.getCreditorId()));
			payment.setDebitorName(accService.getNameOf(payment.getDebitorId()));
		}
		return payments;
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
