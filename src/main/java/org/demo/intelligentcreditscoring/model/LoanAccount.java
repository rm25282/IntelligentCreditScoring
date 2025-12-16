package org.demo.intelligentcreditscoring.model;

public record LoanAccount(LoanAccountType accountType, Long amount, int numberOfMissedPayments) {
}
