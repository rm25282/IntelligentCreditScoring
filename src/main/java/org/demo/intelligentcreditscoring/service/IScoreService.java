package org.demo.intelligentcreditscoring.service;

import org.demo.intelligentcreditscoring.model.CreditScore;

public interface IScoreService {

    /**
     * Calculates the credit score by calling subsystems which have data on the customer
     * <p>
     * Subsystems are:
     * <p>
     * - Whether the customer has any convictions<p>
     * - Whether the customer is registered to vote<p>
     * - The banks accounts held by the customer<p>
     * - The outstanding loans or bills held by the customer<p>
     *
     * <p>
     * Scores are retrieved from each subsystem are between 0 and 100. These are then multiplied by weighted values
     * held in property files. This allows certain subsystem scores to show as more important and therefore
     * having a bigger impact on the final credit score. The overall credit score being returned is between
     * 0 and 1000.
     *
     * @param customerId id of customer
     * @return calculated credit score (0 - 1000)
     */
    CreditScore getCreditScore(int customerId);
}
