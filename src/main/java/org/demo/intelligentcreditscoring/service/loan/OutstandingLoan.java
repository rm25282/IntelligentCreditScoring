package org.demo.intelligentcreditscoring.service.loan;

public record OutstandingLoan(int numberOfMissedPayments, double creditScore) {
}
