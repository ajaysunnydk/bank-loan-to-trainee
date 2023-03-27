package com.techm.loans.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.techm.loans.dao.LoanDAO;
import com.techm.loans.model.Customer;
import com.techm.loans.validator.Validator;

@Service("loanService")
@Transactional
public class LoanServiceImpl implements LoanService {

    private static final Logger logger = LogManager.getLogger(LoanServiceImpl.class);

    @Autowired
    private LoanDAO loanDAO;
    
    @Autowired
    private Validator validator;
    
    @Autowired
    private Environment environment;

    @Override
    public Integer sanctionLoan(Customer customer) throws Exception {
        validator.validate(customer.getLoan());
        int result = loanDAO.checkLoanAllotment(customer.getCustomerId());
        if (result == 0) {
            return loanDAO.sanctionLoan(customer);
        } else if (result == 1) {
            throw new Exception(environment.getProperty("Service.LOAN_ALREADY_TAKEN"));
        } else {
            throw new Exception(environment.getProperty("Service.CUSTOMER_UNAVAILABLE"));
        }
    }



    @Override
    public List<Customer> getReportByLoanType(String loanType) {
        try {
            List<Customer> customerList = loanDAO.getReportByLoanType(loanType);
            if (customerList.isEmpty()) {
                throw new Exception("No loans found for loan type: " + loanType);
            }
            return customerList;
        } catch (Exception e) {
            logger.error("Exception in getReportByLoanType method: " + e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
