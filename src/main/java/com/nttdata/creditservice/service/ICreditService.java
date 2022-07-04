package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.CreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {

    Flux<CreditDto> getAll();

    Mono<CreditDto> getById(String id);

    Mono<CreditDto> register(CreditDto creditDto);

    Mono<CreditDto> updateById(String id, CreditDto creditDto);

    Mono<Void> deleteById(String id);

}
