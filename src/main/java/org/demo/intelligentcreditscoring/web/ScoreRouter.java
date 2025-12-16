package org.demo.intelligentcreditscoring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class ScoreRouter {

    @Bean
    public RouterFunction<ServerResponse> scoreRoute(IScoreHandler scoreHandler) {
        return RouterFunctions.route()
                              .GET("/score/{customerId}",
                                      accept(MediaType.APPLICATION_JSON),
                                      scoreHandler::getScore)
                              .build();
    }
}
