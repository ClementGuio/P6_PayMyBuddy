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
	
	public void saveOrUpdatePayment(Payment payment) {
		repo.save(payment);
	}
	 
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

	public Iterable<Payment> getPaymentsWithDebitorId(Integer debitorId){
		Iterable<Payment> payments = repo.findByDebitorId(debitorId);
		for (Payment payment : payments) {
			payment.setCreditorName(accService.getNameOf(payment.getCreditorId()));
			payment.setDebitorName(accService.getNameOf(payment.getDebitorId()));
		}
		return payments;
	}
	
	public Iterable<Payment> getPaymentsWithCreditorId(Integer creditorId){
		Iterable<Payment> payments = repo.findByCreditorId(creditorId);
		for (Payment payment : payments) {
			payment.setCreditorName(accService.getNameOf(payment.getCreditorId()));
			payment.setDebitorName(accService.getNameOf(payment.getDebitorId()));
		}
		return payments;
	}
	
	public Iterable<Payment> getAllPaymentsOf(Integer accountId){
		Iterable<Payment> payments = repo.findByCreditorIdOrDebitorId(accountId, accountId);
		for (Payment payment : payments) {
			payment.setCreditorName(accService.getNameOf(payment.getCreditorId()));
			payment.setDebitorName(accService.getNameOf(payment.getDebitorId()));
		}
		return payments;
	}
 	
	public void deletePayment(Payment payment) {
		repo.delete(payment);
	}
	
	public Iterable<Payment> getAllPayments(){
		return repo.findAll();
	}

}
