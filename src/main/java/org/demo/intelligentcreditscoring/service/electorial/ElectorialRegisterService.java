package org.demo.intelligentcreditscoring.service.electorial;

import org.demo.intelligentcreditscoring.model.User;
import org.springframework.stereotype.Service;

@Service
public class ElectorialRegisterService implements IElectorialRegisterService {

    private boolean isRegisteredToVote = false;

    @Override
    public ElectorialRegister isRegisteredToVoteScore(User user) {
        // Using the user supplied it could query an electorial register system
        // to discover whether they are registered to vote

        return new ElectorialRegister(isRegisteredToVote, isRegisteredToVote ? 100 : 0);
    }

    @Override
    public void setRegisteredToVote(boolean registeredToVote) {
        isRegisteredToVote = registeredToVote;
    }
}
