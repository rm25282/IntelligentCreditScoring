package org.demo.intelligentcreditscoring.service.electorial;

import org.demo.intelligentcreditscoring.model.User;

public interface IElectorialRegisterService {

    /**
     * Calculates the credit score based on whether they are registered to vote
     *
     * @param user user being queried
     * @return whether they are registered to vote, credit score (0 - 100)
     */
    ElectorialRegister isRegisteredToVoteScore(User user);

    /**
     * Sets test data on whether the user is registered to vote
     *
     * @param registeredToVote true if they are registered to vote, false is not
     */
    void setRegisteredToVote(boolean registeredToVote);
}
