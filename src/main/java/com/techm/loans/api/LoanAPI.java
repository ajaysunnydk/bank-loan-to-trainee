package com.techm.loans.api;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.techm.loans.model.Customer;
import com.techm.loans.service.LoanService;

@RestController
@RequestMapping("/loans")
public class LoanAPI {
    
    @Autowired
    private LoanService loanService;
    
    @Autowired
    private Environment environment;
    
    @PostMapping("/loan")
    public ResponseEntity<?> sanctionLoan(@Valid @RequestBody Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", new Date());
            errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
            errorResponse.put("message", "Validation failed");
            errorResponse.put("path", "/loans/loan");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            int loanId = loanService.sanctionLoan(customer);
            String successMessage = environment.getProperty("API.SANCTION_SUCCESS");
            String message = successMessage + " : " + loanId;
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", new Date());
            errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
            errorResponse.put("path", "/loans/loan");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



    @GetMapping("/{loanType}")
    public ResponseEntity<?> getReportByLoanType(@PathVariable String loanType) {
        try {
            List<Customer> customers = loanService.getReportByLoanType(loanType);
            if (customers.isEmpty()) {
                throw new Exception("No loans found for loan type: " + loanType);
            }
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            if (e.getMessage().equals("No loans found for loan type: " + loanType)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError(environment.getProperty("Service.NO_LOAN_FOUND"), HttpStatus.NOT_FOUND, "/loans/" + loanType));
            } else if (e.getMessage().equals("Invalid loan type: " + loanType)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(environment.getProperty("Service.INVALID_LOAN_TYPE"), HttpStatus.BAD_REQUEST, "/loans/" + loanType));
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("DAO.TECHNICAL_ERROR"), e);
            }
        }
    }


    public class ResponseError {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public ResponseError(String message, HttpStatus status, String path) {
            this.timestamp = LocalDateTime.now().toString();
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.path = path;
        }

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
    }


}
