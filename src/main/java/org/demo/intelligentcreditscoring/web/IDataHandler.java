package org.demo.intelligentcreditscoring.web;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * This allows test data to be added to the system
 */
public interface IDataHandler {

    Mono<ServerResponse> addBankAccount(ServerRequest request);

    Mono<ServerResponse> addLoan(ServerRequest request);

    Mono<ServerResponse> setRegisteredToVote(ServerRequest request);

    Mono<ServerResponse> setConvicted(ServerRequest request);

    Mono<ServerResponse> reset(ServerRequest request);
}
