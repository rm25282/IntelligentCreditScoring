package org.demo.intelligentcreditscoring.web;

import org.demo.intelligentcreditscoring.model.CreditScore;
import org.demo.intelligentcreditscoring.service.IScoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest()
@ContextConfiguration(classes = {ScoreRouter.class, ScoreHandler.class})
class ScoreHandlerTest {

    @Autowired
    RouterFunction<ServerResponse> function;

    @MockitoBean
    IScoreService scoreService;

    @Test
    void shouldReturnScore() {

        WebTestClient client = WebTestClient.bindToRouterFunction(function).build();

        CreditScore returned = new CreditScore(false, 0, false, false, 100.0);
        Mockito.when(scoreService.getCreditScore(1)).thenReturn(returned);

        client.get().uri(uriBuilder -> uriBuilder.path("/score/{customerId}").build(1))
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isOk()
              .expectBody(CreditScore.class).isEqualTo(returned);
    }
}