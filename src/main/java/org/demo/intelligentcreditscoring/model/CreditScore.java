package org.demo.intelligentcreditscoring.model;

public record CreditScore(boolean bankAccountPastOverdrawnLimit, int numberOfMissedPaymentsOnLoans,
                          boolean registeredToVote,
                          boolean convictions, double creditScore) {
}
