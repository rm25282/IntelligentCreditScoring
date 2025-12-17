package org.demo.intelligentcreditscoring.service;

import org.assertj.core.api.Assertions;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    private static final User USER = new User("Richard",
            "Martin",
            LocalDate.of(1985, 12, 12),
            new Address("123 Main Street", "", "Belfast", "BT7 2DE"));
    private final ScoreWeightsProperties scoreWeightsProperties = new ScoreWeightsProperties();
    @Mock
    private ConvictionService convictionService;
    @Mock
    private ElectorialRegisterService electorialRegister;
    @Mock
    private OutstandingLoanService outstandingLoan;
    @Mock
    private BankService bank;

    @Test
    void shouldReturnFullScoreOf1000WhenAllScoresAreMax() {

        scoreWeightsProperties.setHasConvictionsWeight(1.0);
        scoreWeightsProperties.setIsRegisteredToVoteWeight(1.0);
        scoreWeightsProperties.setLoanWeight(4.0);
        scoreWeightsProperties.setBankWeight(4.0);
        ScoreService scoreService = new ScoreService(scoreWeightsProperties, convictionService, electorialRegister, outstandingLoan, bank);

        when(convictionService.calculateConvictionsScore(USER)).thenReturn(new Conviction(false, 100));
        when(electorialRegister.isRegisteredToVoteScore(USER)).thenReturn(new ElectorialRegister(true, 100));
        when(outstandingLoan.calculateOutStandingLoanScore(USER)).thenReturn(new OutstandingLoan(0, 100));
        when(bank.calculateBankAccountScore(USER)).thenReturn(new Bank(false, 100));

        final CreditScore expected = new CreditScore(false, 0, true, false, 1000);
        Assertions.assertThat(scoreService.getCreditScore(1)).isEqualTo(expected);
    }

    @Test
    void shouldReturnZeroWhenAllScoresAre0() {

        scoreWeightsProperties.setHasConvictionsWeight(1.0);
        scoreWeightsProperties.setIsRegisteredToVoteWeight(1.0);
        scoreWeightsProperties.setLoanWeight(4.0);
        scoreWeightsProperties.setBankWeight(4.0);
        ScoreService scoreService = new ScoreService(scoreWeightsProperties, convictionService, electorialRegister, outstandingLoan, bank);

        when(convictionService.calculateConvictionsScore(USER)).thenReturn(new Conviction(true, 0));
        when(electorialRegister.isRegisteredToVoteScore(USER)).thenReturn(new ElectorialRegister(false, 0));
        when(outstandingLoan.calculateOutStandingLoanScore(USER)).thenReturn(new OutstandingLoan(12, 0));
        when(bank.calculateBankAccountScore(USER)).thenReturn(new Bank(true, 0));

        CreditScore expected = new CreditScore(true, 12, false, true, 0);
        Assertions.assertThat(scoreService.getCreditScore(1)).isEqualTo(expected);
    }

    @Test
    void shouldReturn600WhenAllScoresAreMaxExceptLoans() {

        scoreWeightsProperties.setHasConvictionsWeight(1.0);
        scoreWeightsProperties.setIsRegisteredToVoteWeight(1.0);
        scoreWeightsProperties.setLoanWeight(4.0);
        scoreWeightsProperties.setBankWeight(4.0);
        ScoreService scoreService = new ScoreService(scoreWeightsProperties, convictionService, electorialRegister, outstandingLoan, bank);

        when(convictionService.calculateConvictionsScore(USER)).thenReturn(new Conviction(false, 100));
        when(electorialRegister.isRegisteredToVoteScore(USER)).thenReturn(new ElectorialRegister(true, 100));
        when(outstandingLoan.calculateOutStandingLoanScore(USER)).thenReturn(new OutstandingLoan(12, 0));
        when(bank.calculateBankAccountScore(USER)).thenReturn(new Bank(false, 100));


        CreditScore expected = new CreditScore(false, 12, true, false, 600);
        Assertions.assertThat(scoreService.getCreditScore(1)).isEqualTo(expected);
    }

    @Test
    void shouldReturnFullScoreOf1000WhenAllScoresAreMaxButWeightsAdjusted() {

        scoreWeightsProperties.setHasConvictionsWeight(1.0);
        scoreWeightsProperties.setIsRegisteredToVoteWeight(1.0);
        scoreWeightsProperties.setLoanWeight(8.0);
        scoreWeightsProperties.setBankWeight(0.0);
        ScoreService scoreService = new ScoreService(scoreWeightsProperties, convictionService, electorialRegister, outstandingLoan, bank);

        when(convictionService.calculateConvictionsScore(USER)).thenReturn(new Conviction(false, 100));
        when(electorialRegister.isRegisteredToVoteScore(USER)).thenReturn(new ElectorialRegister(true, 100));
        when(outstandingLoan.calculateOutStandingLoanScore(USER)).thenReturn(new OutstandingLoan(0, 100));
        when(bank.calculateBankAccountScore(USER)).thenReturn(new Bank(false, 100));

        CreditScore expected = new CreditScore(false, 0, true, false, 1000);
        Assertions.assertThat(scoreService.getCreditScore(1)).isEqualTo(expected);
    }
}