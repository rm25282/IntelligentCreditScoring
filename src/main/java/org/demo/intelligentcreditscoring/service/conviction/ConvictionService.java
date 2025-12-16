package org.demo.intelligentcreditscoring.service.conviction;

import org.demo.intelligentcreditscoring.model.User;
import org.springframework.stereotype.Service;

@Service
public class ConvictionService implements IConvictionService {

    private boolean hasConvictions = false;

    @Override
    public Conviction calculateConvictionsScore(User user) {
        // Using the user supplied this could query a convictions system
        // to discover whether they are registered to vote
        // just returning score based on boolean

        return new Conviction(hasConvictions, hasConvictions ? 0 : 100);
    }

    @Override
    public void setHasConvictions(boolean hasConvictions) {
        this.hasConvictions = hasConvictions;
    }
}
