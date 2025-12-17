package org.demo.intelligentcreditscoring.web;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * This allows test data to be added to the system
 */
public interface IDataHandler {

    /**
     * Add bank account to the test data
     *
     * @param request Should contain JSON payload
     * @return ServerResponse of ok
     */
    Mono<ServerResponse> addBankAccount(ServerRequest request);

    /**
     * Add a loan account to the test data
     *
     * @param request Should contain JSON payload
     * @return ServerResponse of ok
     */
    Mono<ServerResponse> addLoan(ServerRequest request);

    /**
     * Set the registered to vote flag to true or false
     *
     * @param request should contain request param vote with boolean value
     * @return ServerResponse of ok
     */
    Mono<ServerResponse> setRegisteredToVote(ServerRequest request);

    /**
     * Set the convictions flag to true or false
     *
     * @param request should contain request param with boolean value
     * @return ServerResponse of ok
     */
    Mono<ServerResponse> setConvicted(ServerRequest request);

    Mono<ServerResponse> reset(ServerRequest request);
}
