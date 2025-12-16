package org.demo.intelligentcreditscoring;

import org.demo.intelligentcreditscoring.model.BankAccount;
import org.demo.intelligentcreditscoring.model.CreditScore;
import org.demo.intelligentcreditscoring.model.LoanAccount;
import org.demo.intelligentcreditscoring.model.LoanAccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntelligentCreditScoringApplicationTests {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext context) {
        webTestClient = WebTestClient.bindToApplicationContext(context).configureClient().build();

        webTestClient.delete().uri(uriBuilder -> uriBuilder.path("/data/{customerId}").build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();
    }

    @Test
    void shouldReturnScoreOfEightHundredSixtyWhenVoteTrueAndConvictionsTrueAndBankAccountAndLoanAdded() {

        final BankAccount bankAccount = new BankAccount(-500.00, 500.00, 1);
        final LoanAccount loanAccount = new LoanAccount(LoanAccountType.MORTGAGE, 200000L, 0);

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/convictions")
                                                  .queryParam("convicted", true)
                                                  .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/registered")
                                                  .queryParam("vote", true)
                                                  .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/bankaccount")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(bankAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/loan")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(loanAccount)
                     .exchange()
                     .expectStatus().isOk();

        CreditScore expected = new CreditScore(false, 0, true, true, 860.00);
        webTestClient.get().uri(
                             uriBuilder -> uriBuilder.path("/score/{customerId}")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody(CreditScore.class).isEqualTo(expected);
    }

    @Test
    void shouldReturnScoreOfZeroWhenNotRegisteredToVoteAndConvictedAndNoBankAccountAndLoansMissedPaymentsAreAboveTen() {

        final LoanAccount loanAccount = new LoanAccount(LoanAccountType.MORTGAGE, 200000L, 12);

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/loan")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(loanAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/convictions")
                                                  .queryParam("convicted", true)
                                                  .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/registered")
                                                  .queryParam("vote", false)
                                                  .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();


        CreditScore expected = new CreditScore(false, 12, false, true, 0);
        webTestClient.get().uri(
                             uriBuilder -> uriBuilder.path("/score/{customerId}")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody(CreditScore.class).isEqualTo(expected);
    }

    @Test
    void shouldReturnScoreOf500WhenRegisteredToVoteAndConvictedAndBankAccountAndLoansMissedPaymentsAreAboveTen() {

        final BankAccount bankAccount = new BankAccount(-500.00, 500.00, 0);
        final LoanAccount loanAccount = new LoanAccount(LoanAccountType.MORTGAGE, 200000L, 13);

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/bankaccount")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(bankAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/loan")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(loanAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/convictions")
                                                  .queryParam("convicted", true)
                                                  .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/registered")
                                                  .queryParam("vote", true)
                                                  .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        CreditScore expected = new CreditScore(false, 13, true, true, 500.00);
        webTestClient.get().uri(
                             uriBuilder -> uriBuilder.path("/score/{customerId}")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody(CreditScore.class).isEqualTo(expected);
    }

    @Test
    void shouldReturn200WhenConvictedNotRegisteredToVoteOverdrawnMultipleLoansWithMissedPayments() {

        final BankAccount bankAccount = new BankAccount(-500.00, -600.00, 0);
        final LoanAccount mortgageLoanAccount = new LoanAccount(LoanAccountType.MORTGAGE, 200000L, 1);
        final LoanAccount loanAccount = new LoanAccount(LoanAccountType.LOAN, 10000L, 3);
        final LoanAccount householdBillAccount = new LoanAccount(LoanAccountType.HOUSEHOLD_BILL, 150L, 1);

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/bankaccount")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(bankAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/loan")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(loanAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/loan")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(mortgageLoanAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.post().uri(
                             uriBuilder -> uriBuilder.path("/financial/{customerId}/loan")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(householdBillAccount)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/convictions")
                                                  .queryParam("convicted", true)
                                                  .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/registered")
                                                  .queryParam("vote", false)
                                                  .build(1, false))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        CreditScore expected = new CreditScore(true, 5, false, true, 200.00);
        webTestClient.get().uri(
                             uriBuilder -> uriBuilder.path("/score/{customerId}")
                                                     .build(1))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody(CreditScore.class).isEqualTo(expected);
    }
}
