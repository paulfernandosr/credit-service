package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.BusinessCreditCardDto;
import com.nttdata.creditservice.dto.CreditCardDto;
import com.nttdata.creditservice.dto.PersonalCreditCardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditCardService {

    Flux<CreditCardDto> getAll();

    Mono<CreditCardDto> getById(String id);

    Mono<PersonalCreditCardDto> registerPersonal(PersonalCreditCardDto creditCardDto);

    Mono<BusinessCreditCardDto> registerBusiness(BusinessCreditCardDto creditCardDto);

    Mono<CreditCardDto> updateById(String id, CreditCardDto creditCardDto);

    Mono<Void> deleteById(String id);

}
