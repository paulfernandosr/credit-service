package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.CreditCardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditCardService {

    Flux<CreditCardDto> getAll();

    Mono<CreditCardDto> getById(String id);

    Mono<CreditCardDto> register(CreditCardDto creditCardDto);

    Mono<CreditCardDto> updateById(String id, CreditCardDto creditCardDto);

    Mono<Void> deleteById(String id);

}
