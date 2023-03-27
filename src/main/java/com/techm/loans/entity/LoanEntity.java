package com.techm.loans.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "loan")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="my_seq_gen")
    @SequenceGenerator(name="my_seq_gen", sequenceName="my_seq", initialValue=1000, allocationSize=1)
    @Column(name = "loan_id")
    private Integer loanId;

    @Column(name = "loan_amount")
    private Double loanAmount;
    
    @Column(name = "interest_rate")
    private Double interestRate;
    
    @Column(name = "term")
    private Integer term;
    
    @Column(name = "loan_type")
    private String loanType;

    public LoanEntity() {}

    public LoanEntity(Integer loanId, Double loanAmount, Double interestRate, Integer term, String loanType) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.term = term;
        this.loanType = loanType;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LoanEntity)) {
            return false;
        }
        LoanEntity loan = (LoanEntity) o;
        return loan.canEqual(this) && Objects.equals(loanId, loan.loanId);
    }

    public boolean canEqual(Object o) {
        return o instanceof LoanEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(loanId);
    }

    @Override
    public String toString() {
        return "LoanEntity{" +
                "loanId=" + loanId +
                ", loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                ", term=" + term +
                ", loanType='" + loanType + '\'' +
                '}';
    }
}

