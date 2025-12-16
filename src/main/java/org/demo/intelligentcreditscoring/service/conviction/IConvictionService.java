package org.demo.intelligentcreditscoring.service.conviction;

import org.demo.intelligentcreditscoring.model.User;

public interface IConvictionService {

    /**
     *
     * Calculates the credit score based on whether ths user has any convictions or not
     *
     * @param user user with details
     * @return whether the user has convictions, credit score (0 - 100)
     */
    Conviction calculateConvictionsScore(User user);


    /**
     * Used to manipulate the data for testing
     *
     * @param hasConvictions set whether the user has convictions
     */
    void setHasConvictions(boolean hasConvictions);
}
