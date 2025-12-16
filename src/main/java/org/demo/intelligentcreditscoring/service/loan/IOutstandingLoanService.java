package org.demo.intelligentcreditscoring.service.loan;

import org.demo.intelligentcreditscoring.model.User;

@FunctionalInterface
public interface IOutstandingLoanService {

    /**
     * Calculate credit score based on the loans that the user has. These are store in an
     * in memory database.
     *
     * @param user user being queried
     * @return numberOfMissedPayments, credit score (0 - 100)
     */
    OutstandingLoan calculateOutStandingLoanScore(User user);

}
