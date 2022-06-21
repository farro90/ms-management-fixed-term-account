package com.nttdata.bc19.msmanagementfixedtermaccount.service;

import com.nttdata.bc19.msmanagementfixedtermaccount.model.FixedTermAccountPerson;
import com.nttdata.bc19.msmanagementfixedtermaccount.request.FixedTermAccountPersonRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IManagementFixedTermAccountPersonService {

    Mono<FixedTermAccountPerson> create(FixedTermAccountPersonRequest fixedTermAccountPersonRequest);
    Mono<FixedTermAccountPerson> update(FixedTermAccountPerson fixedTermAccountPerson);
    Mono<Void>deleteById(String id);
    Mono<FixedTermAccountPerson> findById(String id);
    Flux<FixedTermAccountPerson> findAll();

    Mono<FixedTermAccountPerson> findByAccountNumber(String accountNumber);
    Mono<Boolean> existsByAccountNumber(String accountNumber);

}
