package com.techm.loans.bankloantotrainee;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.techm.loans.api.LoanAPI;
import com.techm.loans.model.Customer;
import com.techm.loans.service.LoanService;

class LoanAPITest {

    @InjectMocks
    LoanAPI loanAPI;

    @Mock
    LoanService loanServiceMock;

    @Mock
    BindingResult bindingResultMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test for successful loan sanction")
    void testSanctionLoanSuccess() throws Exception {
        Customer customer = new Customer();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(loanServiceMock.sanctionLoan(any(Customer.class))).thenReturn(1234);
        ResponseEntity<?> response = loanAPI.sanctionLoan(customer, bindingResultMock);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Loan sanctioned successfully : 1234", response.getBody());
    }

    @Test
    @DisplayName("Test for failed loan sanction")
    void testSanctionLoanFailure() throws Exception {
        Customer customer = new Customer();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(loanServiceMock.sanctionLoan(any(Customer.class))).thenThrow(new RuntimeException("Unable to sanction loan"));
        ResponseEntity<?> response = loanAPI.sanctionLoan(customer, bindingResultMock);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Unable to sanction loan", ((ResponseEntity<?>) response).getBody());
    }

    @Test
    @DisplayName("Test for invalid loan type")
    void testGetReportByLoanTypeInvalidType() throws Exception {
        String loanType = "invalid";
        when(loanServiceMock.getReportByLoanType(loanType)).thenThrow(new Exception("Invalid loan type: " + loanType));
        ResponseEntity<?> response = loanAPI.getReportByLoanType(loanType);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Invalid loan type: " + loanType, ((LoanAPI.ResponseError) response.getBody()).getMessage());
    }

    @Test
    @DisplayName("Test for no loans found")
    void testGetReportByLoanTypeNoLoansFound() throws Exception {
        String loanType = "home";
        List<Customer> customers = new ArrayList<>();
        when(loanServiceMock.getReportByLoanType(loanType)).thenReturn(customers);
        ResponseEntity<?> response = loanAPI.getReportByLoanType(loanType);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("No loans found for loan type: " + loanType, ((LoanAPI.ResponseError) response.getBody()).getMessage());
    }

    @Test
    @DisplayName("Test for successful loan report")
    void testGetReportByLoanTypeSuccess() throws Exception {
        String loanType = "home";
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        when(loanServiceMock.getReportByLoanType(loanType)).thenReturn(customers);
        ResponseEntity<?> response = loanAPI.getReportByLoanType(loanType);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(customers, response.getBody());
    }

}
