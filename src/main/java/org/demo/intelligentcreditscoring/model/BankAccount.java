package org.demo.intelligentcreditscoring.model;

public record BankAccount(Double overdraftLimit, Double currentBalance, Integer numberOfTimesOverdrawn) {
}
