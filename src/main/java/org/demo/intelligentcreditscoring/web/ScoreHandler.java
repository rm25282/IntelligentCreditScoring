package org.demo.intelligentcreditscoring.web;

import org.demo.intelligentcreditscoring.service.IScoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class ScoreHandler implements IScoreHandler {

    private final IScoreService scoreService;

    public ScoreHandler(IScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    public Mono<ServerResponse> getCreditScore(ServerRequest request) {
        final String customerId = request.pathVariable("customerId");
        return ok().bodyValue(scoreService.getCreditScore(Integer.parseInt(customerId)));
    }

}
