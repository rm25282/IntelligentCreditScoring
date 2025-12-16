package org.demo.intelligentcreditscoring.repository.model;

import jakarta.persistence.*;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private LoanType loanType;
    private Long loanAmount;
    private int missedPayments;

    // Empty constructor required for JPA
    public Loan() {
    }

    public Loan(LoanType loanType, Long loanAmount, int missedPayments) {
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.missedPayments = missedPayments;
    }

    public int getMissedPayments() {
        return missedPayments;
    }

    public Long getLoanAmount() {
        return loanAmount;
    }

    public Long getId() {
        return id;
    }

    public LoanType getLoanType() {
        return loanType;
    }

}
