package org.demo.intelligentcreditscoring.web;

import org.demo.intelligentcreditscoring.model.BankAccount;
import org.demo.intelligentcreditscoring.model.LoanAccount;
import org.demo.intelligentcreditscoring.service.IDataService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class DataHandler implements IDataHandler {

    private final IDataService dataService;

    public DataHandler(IDataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Mono<ServerResponse> addBankAccount(ServerRequest request) {
        return request.bodyToMono(BankAccount.class).flatMap(bankAccount -> {
            dataService.addBankAccount(bankAccount);
            return ok().build();
        });
    }

    @Override
    public Mono<ServerResponse> addLoan(ServerRequest request) {
        return request.bodyToMono(LoanAccount.class).flatMap(loan -> {
            dataService.addLoan(loan);
            return ok().build();
        });
    }

    @Override
    public Mono<ServerResponse> setRegisteredToVote(ServerRequest request) {
        boolean canVote = Boolean.parseBoolean(request.queryParam("vote").orElseThrow());

        dataService.setRegisterToVote(canVote);
        return ok().build();
    }

    @Override
    public Mono<ServerResponse> setConvicted(ServerRequest request) {
        boolean convicted = Boolean.parseBoolean(request.queryParam("convicted").orElseThrow());

        dataService.setConvictions(convicted);
        return ok().build();
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest request) {
        dataService.reset();

        return ok().build();
    }
}
