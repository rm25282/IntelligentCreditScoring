package org.demo.intelligentcreditscoring.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("org.demo.intelligentcreditscoring.weights")
public class ScoreWeightsProperties {
    private double hasConvictionsWeight;
    private double isRegisteredToVoteWeight;
    private double loanWeight;
    private double bankWeight;

    public double getHasConvictionsWeight() {
        return hasConvictionsWeight;
    }

    public void setHasConvictionsWeight(double hasConvictionsWeight) {
        this.hasConvictionsWeight = hasConvictionsWeight;
    }

    public double getIsRegisteredToVoteWeight() {
        return isRegisteredToVoteWeight;
    }

    public void setIsRegisteredToVoteWeight(double isRegisteredToVoteWeight) {
        this.isRegisteredToVoteWeight = isRegisteredToVoteWeight;
    }

    public double getLoanWeight() {
        return loanWeight;
    }

    public void setLoanWeight(double loanWeight) {
        this.loanWeight = loanWeight;
    }

    public double getBankWeight() {
        return bankWeight;
    }

    public void setBankWeight(double bankWeight) {
        this.bankWeight = bankWeight;
    }
}
