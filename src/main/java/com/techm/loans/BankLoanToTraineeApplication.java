package com.techm.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.techm.loans.entity"})
@SpringBootApplication
public class BankLoanToTraineeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankLoanToTraineeApplication.class, args);
	}

}
