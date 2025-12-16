package org.demo.intelligentcreditscoring.service;

import org.demo.intelligentcreditscoring.model.BankAccount;
import org.demo.intelligentcreditscoring.model.LoanAccount;

public interface IDataService {
    /**
     * Add a bank account to the test data
     *
     * @param bankAccount bank account details
     */
    void addBankAccount(BankAccount bankAccount);

    /**
     * Add a loan to the test data
     *
     * @param loanAccount loan account details
     */
    void addLoan(LoanAccount loanAccount);


    /**
     * Sets whether the user can register to vote or not
     *
     * @param canVote true if they can vote, false if not
     */
    void setRegisterToVote(boolean canVote);

    /**
     * Sets whether the user has convictions
     *
     * @param hasBeenConvicted true if the user has convictions, false if not
     */
    void setConvictions(boolean hasBeenConvicted);


    /**
     * Used for testing in order to reset the test data back to defaults
     * <p>
     * ie no bank accounts or loans, no convictions and not registered to vote
     */
    void reset();
}
