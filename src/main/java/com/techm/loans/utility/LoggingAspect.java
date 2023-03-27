package com.techm.loans.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.techm.loans.service.LoanServiceImpl.sanctionLoan(..)) throws java.lang.Exception")
    public void loanServiceSanctionLoanException() {}

    @Pointcut("execution(* com.techm.loans.service.LoanServiceImpl.getReportByLoanType(..)) throws java.lang.Exception")
    public void loanServiceGetReportByLoanTypeException() {}

    @Pointcut("execution(* com.techm.loans.dao.LoanDAOImpl.*(..)) throws java.lang.Exception")
    public void loanDaoException() {}

    @AfterThrowing(pointcut = "loanServiceSanctionLoanException()", throwing = "exception")
    public void logServiceException(Exception exception) {
        logger.error("Exception in LoanService.sanctionLoan method: " + exception.getMessage(), exception);
    }

    @AfterThrowing(pointcut = "loanServiceGetReportByLoanTypeException()", throwing = "exception")
    public void logServiceException2(Exception exception) {
        logger.error("Exception in LoanService.getReportByLoanType method: " + exception.getMessage(), exception);
    }

    @AfterThrowing(pointcut = "loanDaoException()", throwing = "exception")
    public void logDaoException(Exception exception) {
        logger.error("Exception in LoanDAOImpl method: " + exception.getMessage(), exception);
    }
}
