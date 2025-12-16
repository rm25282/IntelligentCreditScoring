package org.demo.intelligentcreditscoring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class DataRouter {

    @Bean
    public RouterFunction<ServerResponse> dataRoute(IDataHandler dataHandler) {
        return RouterFunctions.route()
                              .POST("/financial/{customerId}/bankaccount",
                                      accept(MediaType.APPLICATION_JSON),
                                      dataHandler::addBankAccount)
                              .POST("/financial/{customerId}/loan",
                                      accept(MediaType.APPLICATION_JSON),
                                      dataHandler::addLoan)
                              .PUT("/legal/{customerId}/registered",
                                      accept(MediaType.APPLICATION_JSON),
                                      dataHandler::setRegisteredToVote)
                              .PUT("/legal/{customerId}/convictions",
                                      accept(MediaType.APPLICATION_JSON),
                                      dataHandler::setConvicted)
                              .DELETE("/data/{customerId}", accept(MediaType.APPLICATION_JSON),
                                      dataHandler::reset)
                              .build();
    }
}
