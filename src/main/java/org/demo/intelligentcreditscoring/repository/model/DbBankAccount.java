package org.demo.intelligentcreditscoring.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DbBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double overdraftLimit;
    private Double currentBalance;
    private Integer numberOfTimesOverdrawn;

    // Empty constructor required for JPA
    public DbBankAccount() {
    }

    public DbBankAccount(Double overdraftLimit, Double currentBalance, Integer numberOfTimesOverdrawn) {
        this.overdraftLimit = overdraftLimit;
        this.currentBalance = currentBalance;
        this.numberOfTimesOverdrawn = numberOfTimesOverdrawn;
    }

    public Integer getNumberOfTimesOverdrawn() {
        return numberOfTimesOverdrawn;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public Double getOverdraftLimit() {
        return overdraftLimit;
    }

}
