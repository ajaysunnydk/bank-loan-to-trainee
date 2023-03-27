package com.techm.loans.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.techm.loans.entity.CustomerEntity;
import com.techm.loans.entity.LoanEntity;
import com.techm.loans.model.Customer;
import com.techm.loans.model.Loan;

@Repository("loanDAO")
public class LoanDAOImpl implements LoanDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> getReportByLoanType(String loanType) {
    	String query = "SELECT c FROM CustomerEntity c JOIN c.loan l WHERE UPPER(l.loanType) = UPPER(:loanType)";
    	List<CustomerEntity> customers = entityManager.createQuery(query, CustomerEntity.class)
    	                                            .setParameter("loanType", loanType)
    	                                            .getResultList();

    	List<Customer> report = new ArrayList<>();
    	for(CustomerEntity customer : customers) {
    	    LoanEntity loan = customer.getLoan();
    	    double emi = (loan.getLoanAmount() + ((loan.getLoanAmount() * loan.getTerm() * loan.getInterestRate()) / 100)) / (loan.getTerm() * 12);
    	    emi = Math.ceil(emi);

    	    Customer c = new Customer();
    	    c.setCustomerId(customer.getCustomerId());
    	    c.setCustomerName(customer.getCustomerName());
    	    c.setMobileNo(customer.getMobileNo());

    	    Loan l = new Loan();
    	    l.setLoanId(loan.getLoanId());
    	    l.setLoanAmount(loan.getLoanAmount());
    	    l.setInterestRate(loan.getInterestRate());
    	    l.setTerm(loan.getTerm());
    	    l.setLoanType(loan.getLoanType());

    	    c.setLoan(l);
    	    c.setEmi(emi);

    	    report.add(c);
    	}

    	return report;

    }

    @Override
    public Integer checkLoanAllotment(Integer customerId) {
        CustomerEntity customer = entityManager.find(CustomerEntity.class, customerId);
        if(customer == null) {
            return -1;
        }
        else if(customer.getLoan() == null) {
            return 0;
        }
        else {
            return 1;
        }
    }


    @Override
    public Integer sanctionLoan(Customer customer) {
        CustomerEntity existingCustomer = entityManager.find(CustomerEntity.class, customer.getCustomerId());
        if(existingCustomer == null) {
            return -1;
        }

        LoanEntity loan = new LoanEntity();
        loan.setLoanAmount(customer.getLoan().getLoanAmount());
        loan.setInterestRate(customer.getLoan().getInterestRate());
        loan.setLoanType(customer.getLoan().getLoanType());

        if(customer.getLoan().getLoanType().equals("HomeLoan")) {
            loan.setTerm(15);
            loan.setInterestRate(13.0);
        }
        else {
            loan.setTerm(5);
            loan.setInterestRate(9.0);
        }

        existingCustomer.setLoan(loan);
        entityManager.persist(loan);

        return loan.getLoanId();
    }
}
