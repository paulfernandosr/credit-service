package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {

    Flux<CreditDto> getAll();

    Mono<CreditDto> getById(String id);

    Mono<CreditDto> register(CreditDto creditDto);

    Mono<PersonalCreditDto> registerPersonal(PersonalCreditDto creditDto);

    Mono<BusinessCreditDto> registerBusiness(BusinessCreditDto creditDto);

    Mono<CreditDto> updateById(String id, CreditDto creditDto);

    Mono<Void> deleteById(String id);

}
