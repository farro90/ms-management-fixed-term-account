package com.nttdata.bc19.msmanagementfixedtermaccount.repository;

import com.nttdata.bc19.msmanagementfixedtermaccount.model.FixedTermAccountPerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IFixedTermAccountPersonRepository extends ReactiveMongoRepository<FixedTermAccountPerson, String> {
    Flux<FixedTermAccountPerson> findByIdPersonClient(String id);

    Mono<Long> countByIdPersonClient(String id);

    Flux<FixedTermAccountPerson> findByIdPersonClientAndIdPasiveProduct(String idPersonClient, String idPasiveProduct);

    Mono<Long> countByIdPersonClientAndIdPasiveProduct(String idPersonClient, String idPasiveProduct);

    Mono<FixedTermAccountPerson> findByAccountNumber(String accountNumber);

    Mono<Boolean> existsByAccountNumber(String accountNumber);
}
