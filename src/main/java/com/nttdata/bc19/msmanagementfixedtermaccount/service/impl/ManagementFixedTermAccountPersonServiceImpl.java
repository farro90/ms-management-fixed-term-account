package com.nttdata.bc19.msmanagementfixedtermaccount.service.impl;

import com.nttdata.bc19.msmanagementfixedtermaccount.exception.ModelNotFoundException;
import com.nttdata.bc19.msmanagementfixedtermaccount.model.FixedTermAccountPerson;
import com.nttdata.bc19.msmanagementfixedtermaccount.model.responseWC.PasiveProduct;
import com.nttdata.bc19.msmanagementfixedtermaccount.model.responseWC.PersonClient;
import com.nttdata.bc19.msmanagementfixedtermaccount.repository.IFixedTermAccountPersonRepository;
import com.nttdata.bc19.msmanagementfixedtermaccount.request.FixedTermAccountPersonRequest;
import com.nttdata.bc19.msmanagementfixedtermaccount.service.IManagementFixedTermAccountPersonService;
import com.nttdata.bc19.msmanagementfixedtermaccount.util.PasiveProductType;
import com.nttdata.bc19.msmanagementfixedtermaccount.webclient.impl.ServiceWCImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ManagementFixedTermAccountPersonServiceImpl implements IManagementFixedTermAccountPersonService {

    @Autowired
    IFixedTermAccountPersonRepository fixedTermAccountPersonRepository;

    @Autowired
    private ServiceWCImpl clientServiceWC;

    @Override
    public Mono<FixedTermAccountPerson> create(FixedTermAccountPersonRequest fixedTermAccountPersonRequest) {
        return clientServiceWC.findPersonClientById(fixedTermAccountPersonRequest.getIdPersonClient())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(personClientResponse ->
                        clientServiceWC.findPasiveProductById(fixedTermAccountPersonRequest.getIdPasiveProduct())
                                .switchIfEmpty(Mono.error(new Exception()))
                                .flatMap(fixedTermAccountResponse ->
                                        fixedTermAccountPersonRepository.countByIdPersonClientAndIdPasiveProduct(fixedTermAccountPersonRequest.getIdPersonClient(), fixedTermAccountPersonRequest.getIdPasiveProduct())
                                                .switchIfEmpty(Mono.error(new Exception()))
                                                .flatMap(fixedTermAccountCountResponse -> this.businessLogicSavingAccountPerson(personClientResponse, fixedTermAccountResponse, fixedTermAccountPersonRequest, fixedTermAccountCountResponse))
                                )
                        );
    }

    @Override
    public Mono<FixedTermAccountPerson> update(FixedTermAccountPerson fixedTermAccountPerson) {
        fixedTermAccountPerson.setUpdatedAt(LocalDateTime.now());
        return fixedTermAccountPersonRepository.save(fixedTermAccountPerson);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return fixedTermAccountPersonRepository.deleteById(id);
    }

    @Override
    public Mono<FixedTermAccountPerson> findById(String id) {
        return fixedTermAccountPersonRepository.findById(id);
    }

    @Override
    public Flux<FixedTermAccountPerson> findAll() {
        return fixedTermAccountPersonRepository.findAll();
    }

    @Override
    public Mono<FixedTermAccountPerson> findByAccountNumber(String accountNumber) {
        return fixedTermAccountPersonRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Mono<Boolean> existsByAccountNumber(String accountNumber) {
        return fixedTermAccountPersonRepository.existsByAccountNumber(accountNumber);
    }

    private Mono<FixedTermAccountPerson> businessLogicSavingAccountPerson(PersonClient personClient, PasiveProduct fixedTermAccount, FixedTermAccountPersonRequest fixedTermAccountPersonRequest, Long fixedTermAccountCount){
        FixedTermAccountPerson fixedTermAccountPerson = new FixedTermAccountPerson();
        fixedTermAccountPerson.setId(new ObjectId().toString());
        fixedTermAccountPerson.setCreatedAt(LocalDateTime.now());
        fixedTermAccountPerson.setAmount(fixedTermAccountPersonRequest.getAmount());
        fixedTermAccountPerson.setAccountNumber(fixedTermAccountPersonRequest.getAccountNumber()); //Generate number account
        fixedTermAccountPerson.setLastTrasactionDate(LocalDateTime.now());
        fixedTermAccountPerson.setIdPersonClient(fixedTermAccountPersonRequest.getIdPersonClient());
        fixedTermAccountPerson.setIdPasiveProduct(fixedTermAccountPersonRequest.getIdPasiveProduct());
        fixedTermAccountPerson.setPersonClient(personClient);
        fixedTermAccountPerson.setPasiveProduct(fixedTermAccount);
        fixedTermAccountPerson.setNumberMovements(0);

        boolean existsClientPersonPasiveProduct = fixedTermAccountCount > 0 ? true : false;

        if(!fixedTermAccount.getName().equals(PasiveProductType.PLAZOFIJO.name())){
            return Mono.error(new ModelNotFoundException("The account is not fixed term."));
        }
        if(!fixedTermAccount.getAllowPersonClient()) {
            return Mono.error(new ModelNotFoundException("Type of account not allowed for person client"));
        }
        if(existsClientPersonPasiveProduct) {
            return Mono.error(new ModelNotFoundException("The customer already has an account"));
        }
        if(fixedTermAccount.getMinimumOpeningAmount() > fixedTermAccountPersonRequest.getAmount()) {
            return Mono.error(new ModelNotFoundException("The minimum amount for opening this account is greater than the amount deposited"));
        }
        else{
            return fixedTermAccountPersonRepository.save(fixedTermAccountPerson);
        }
    }
}
