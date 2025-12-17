package org.demo.intelligentcreditscoring.web;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IScoreHandler<T extends ServerResponse> {
    /**
     * Request a credit score for the customer based on data submitted.
     * <p>
     * Credit score is calculated based on
     * - bank accounts being in credit and number of missed payments
     * - loan accounts missed payments
     * - whether customer is registered to vote
     * - whether the customer has any previous convictions
     *
     * @param request Should contain path variable for customer id
     * @return ServerResponse of ok
     */
    Mono<ServerResponse> getCreditScore(ServerRequest request);
}

