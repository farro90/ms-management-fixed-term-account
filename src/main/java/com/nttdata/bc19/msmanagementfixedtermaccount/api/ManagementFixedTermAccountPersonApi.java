package com.nttdata.bc19.msmanagementfixedtermaccount.api;

import com.nttdata.bc19.msmanagementfixedtermaccount.model.FixedTermAccountPerson;
import com.nttdata.bc19.msmanagementfixedtermaccount.request.FixedTermAccountPersonRequest;
import com.nttdata.bc19.msmanagementfixedtermaccount.service.IManagementFixedTermAccountPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/fixed-term-account")
public class ManagementFixedTermAccountPersonApi {

    @Autowired
    private IManagementFixedTermAccountPersonService managementFixedTermAccountPersonService;

    @PostMapping()
    public Mono<FixedTermAccountPerson> create(@RequestBody FixedTermAccountPersonRequest fixedTermAccountPersonRequest){
        return managementFixedTermAccountPersonService.create(fixedTermAccountPersonRequest);
    }

    @PutMapping()
    public Mono<FixedTermAccountPerson> update(@RequestBody FixedTermAccountPerson fixedTermAccountPerson){
        return managementFixedTermAccountPersonService.update(fixedTermAccountPerson);
    }

    @GetMapping()
    public Flux<FixedTermAccountPerson> findAll(){
        return managementFixedTermAccountPersonService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<FixedTermAccountPerson> findById(@PathVariable String id){ return managementFixedTermAccountPersonService.findById(id); }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteSA(@PathVariable String id){
        return managementFixedTermAccountPersonService.deleteById(id);
    }

    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Mono<FixedTermAccountPerson> findByAccountNumber(@PathVariable String accountNumber){ return managementFixedTermAccountPersonService.findByAccountNumber(accountNumber); }
}
