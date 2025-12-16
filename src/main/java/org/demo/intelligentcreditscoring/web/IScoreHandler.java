package org.demo.intelligentcreditscoring.web;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IScoreHandler<T extends ServerResponse> {
    Mono<ServerResponse> getScore(ServerRequest request);
}

