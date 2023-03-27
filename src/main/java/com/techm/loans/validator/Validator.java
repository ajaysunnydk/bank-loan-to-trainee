package com.techm.loans.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.techm.loans.model.Loan;

@Component
public class Validator {
	
    
    @Autowired
    private Environment environment;

    public void validate(Loan loan) throws Exception {
        boolean isValidLoanType = validateLoanType(loan.getLoanType());
        if (!isValidLoanType) {
            String errorMessage = environment.getProperty("Validator.INVALID_LOANTYPE");
            throw new Exception(errorMessage);
        }
    }


    public boolean validateLoanType(String loanType) {
        return loanType.equals("HomeLoan") || loanType.equals("CarLoan");
    }
}

