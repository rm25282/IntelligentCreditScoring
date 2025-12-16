package org.demo.intelligentcreditscoring.model;

public record CreditScore(boolean bankAccountOverdrawn, int numberOfMissedPaymentsOnLoans, boolean registeredToVote,
                          boolean convictions, double creditScore) {
}
