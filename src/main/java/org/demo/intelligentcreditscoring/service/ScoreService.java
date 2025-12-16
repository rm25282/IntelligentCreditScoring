package org.demo.intelligentcreditscoring.service;

import org.demo.intelligentcreditscoring.model.Address;
import org.demo.intelligentcreditscoring.model.CreditScore;
import org.demo.intelligentcreditscoring.model.User;
import org.demo.intelligentcreditscoring.service.bank.Bank;
import org.demo.intelligentcreditscoring.service.bank.BankService;
import org.demo.intelligentcreditscoring.service.conviction.Conviction;
import org.demo.intelligentcreditscoring.service.conviction.ConvictionService;
import org.demo.intelligentcreditscoring.service.electorial.ElectorialRegister;
import org.demo.intelligentcreditscoring.service.electorial.ElectorialRegisterService;
import org.demo.intelligentcreditscoring.service.loan.OutstandingLoan;
import org.demo.intelligentcreditscoring.service.loan.OutstandingLoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScoreService implements IScoreService {

    private final ScoreWeightsProperties scoreWeightsProperties;
    private final ConvictionService convictionService;
    private final ElectorialRegisterService electorialRegister;
    private final OutstandingLoanService outstandingLoan;
    private final BankService bank;

    public ScoreService(ScoreWeightsProperties scoreWeightsProperties, ConvictionService convictionService, ElectorialRegisterService electorialRegister, OutstandingLoanService outstandingLoan, BankService bank) {
        this.scoreWeightsProperties = scoreWeightsProperties;
        this.convictionService = convictionService;
        this.electorialRegister = electorialRegister;
        this.outstandingLoan = outstandingLoan;
        this.bank = bank;
    }

    @Override
    public CreditScore getCreditScore(int customerId) {

        // TODO in reality the customer id should be retrieve the user from another system to get the details below
        Address address = new Address("123 Main Street", "", "Belfast", "BT7 2DE");
        final User user = new User("Richard", "Martin", LocalDate.of(1985, 12, 12), address);

        final Conviction hasConvictionsScore = convictionService.calculateConvictionsScore(user);

        final ElectorialRegister isRegisteredToVoteScore = electorialRegister.isRegisteredToVoteScore(user);

        final OutstandingLoan loanScore = outstandingLoan.calculateOutStandingLoanScore(user);

        final Bank bankScore = bank.calculateBankAccountScore(user);

        final double creditScore = hasConvictionsScore.convictionScore() * scoreWeightsProperties.getHasConvictionsWeight() +
                isRegisteredToVoteScore.registeredScore() * scoreWeightsProperties.getIsRegisteredToVoteWeight() +
                loanScore.creditScore() * scoreWeightsProperties.getLoanWeight() +
                bankScore.bankCreditScore() * scoreWeightsProperties.getBankWeight();

        return new CreditScore(bankScore.currentlyOverdrawn(), loanScore.numberOfMissedPayments(), isRegisteredToVoteScore.registered(), hasConvictionsScore.hasConvictions(), creditScore);

    }
}
