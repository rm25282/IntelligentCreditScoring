package org.demo.intelligentcreditscoring.web;

import org.demo.intelligentcreditscoring.model.BankAccount;
import org.demo.intelligentcreditscoring.model.LoanAccount;
import org.demo.intelligentcreditscoring.model.LoanAccountType;
import org.demo.intelligentcreditscoring.service.IDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@ContextConfiguration(classes = {DataRouter.class, DataHandler.class})
class DataHandlerTest {

    @Autowired
    RouterFunction<ServerResponse> function;

    @MockitoBean
    IDataService dataService;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToRouterFunction(function).build();
    }

    @Test
    void ShouldAcceptBankAccount() {

        client = WebTestClient.bindToRouterFunction(function).build();

        final BankAccount bankAccount = new BankAccount(-500.00, 500.00, 1);

        client.post().uri(uriBuilder -> uriBuilder.path("/financial/{customerId}/bankaccount").build(1))
              .accept(MediaType.APPLICATION_JSON)
              .bodyValue(bankAccount)
              .exchange()
              .expectStatus().isOk();
    }

    @Test
    void ShouldAcceptBankLoan() {

        final LoanAccount loanAccount = new LoanAccount(LoanAccountType.MORTGAGE, 200000L, 0);

        client.post().uri(uriBuilder -> uriBuilder.path("/financial/{customerId}/loan").build(1))
              .accept(MediaType.APPLICATION_JSON)
              .bodyValue(loanAccount)
              .exchange()
              .expectStatus().isOk();
    }

    @Test
    void ShouldAcceptSetConvictions() {
        client.put().uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/convictions")
                                                 .queryParam("convicted", true).build(1))
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isOk();
    }

    @Test
    void ShouldAcceptSetRegisteredToVote() {
        client.put().uri(uriBuilder -> uriBuilder.path("/legal/{customerId}/registered")
                                                 .queryParam("vote", true).build(1, true))
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isOk();
    }

    @Test
    void shouldResetSystem() {
        client.delete().uri(uriBuilder -> uriBuilder.path("/data/{customerId}").build(1))
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isOk();
    }
}