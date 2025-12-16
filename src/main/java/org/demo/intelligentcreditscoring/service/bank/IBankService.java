package org.demo.intelligentcreditscoring.service.bank;

import org.demo.intelligentcreditscoring.model.User;

@FunctionalInterface
public interface IBankService {

    /**
     * Calculates a credit score based on the users bank accounts. The bank account details
     * are stored in an in memory database
     *
     * @param user the user requested
     * @return if the user is currently overdrawn,
     * how many times they have been overdrawn,
     * the credit score of the user (0 - 100)
     */
    Bank calculateBankAccountScore(User user);

}
