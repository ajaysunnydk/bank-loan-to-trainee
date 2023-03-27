package com.techm.loans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "name")
    private String customerName;
    
    @Column(name = "mobile_no")
    private Long mobileNo;

    @OneToOne
    @JoinColumn(name = "loan_id")
    private LoanEntity loan;

    public CustomerEntity() {}

    public CustomerEntity(Integer customerId, String customerName, Long mobileNo, LoanEntity loan) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.loan = loan;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LoanEntity getLoan() {
        return loan;
    }

	public void setLoan(LoanEntity loan) {
		this.loan = loan;
	}

	@Override
	public String toString() {
		return "CustomerEntity [customerId=" + customerId + ", customerName="
				+ customerName + ", mobileNo=" + mobileNo + ", loan=" + loan + "]";
	}

}
