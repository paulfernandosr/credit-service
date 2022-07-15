package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.CreditDto;
import com.nttdata.creditservice.dto.request.BusinessCreditDto;
import com.nttdata.creditservice.dto.request.CreditCardDto;
import com.nttdata.creditservice.dto.request.PersonalCreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {

    Flux<CreditDto> getAll();

    Flux<CreditDto> getByCustomerId(String customerId);

    Mono<CreditDto> getById(String id);

    Mono<CreditDto> registerPersonalCredit(PersonalCreditDto personalCreditDto);

    Mono<CreditDto> registerBusinessCredit(BusinessCreditDto businessCreditDto);

    Mono<CreditDto> registerPersonalCreditCard(CreditCardDto creditCardDto);

    Mono<CreditDto> registerBusinessCreditCard(CreditCardDto creditCardDto);

    Mono<CreditDto> updateById(String id, CreditDto creditDto);

    Mono<Void> deleteById(String id);

}
