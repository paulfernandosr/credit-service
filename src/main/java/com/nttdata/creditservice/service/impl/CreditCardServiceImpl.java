package com.nttdata.creditservice.service.impl;

import com.nttdata.creditservice.dto.CreditCardDto;
import com.nttdata.creditservice.exception.BadRequestException;
import com.nttdata.creditservice.exception.CreditNotFoundException;
import com.nttdata.creditservice.exception.DuplicateCreditException;
import com.nttdata.creditservice.repo.ICreditCardRepo;
import com.nttdata.creditservice.service.ICreditCardService;
import com.nttdata.creditservice.service.ICustomerService;
import com.nttdata.creditservice.util.Constants;
import com.nttdata.creditservice.util.CreditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements ICreditCardService {

    private final ICreditCardRepo repo;
    private final ICustomerService customerService;

    @Override
    public Flux<CreditCardDto> getAll() {
        return repo.findAll().map(CreditMapper::toDto);
    }

    @Override
    public Mono<CreditCardDto> getById(String id) {
        return repo.findById(id)
                .map(CreditMapper::toDto)
                .switchIfEmpty(Mono.error(new CreditNotFoundException(String.format(Constants.CREDIT_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<CreditCardDto> register(CreditCardDto creditCardDto) {
        Mono<CreditCardDto> registeredCreditCard = Mono.just(CreditMapper.toModel(creditCardDto))
                .flatMap(credit -> repo.existsByCardNumber(credit.getCardNumber())
                        .flatMap(exists -> (!exists)
                                ? repo.save(credit.toBuilder().id(null).build()).map(CreditMapper::toDto)
                                : Mono.error(new DuplicateCreditException(
                                String.format(Constants.CREDIT_DUPLICATED_BY_A_FIELD,
                                        Constants.CARD_NUMBER, credit.getCardNumber())))));

        if (creditCardDto.getPersonalCustomerId() != null) {
            return customerService.getPersonalCustomerById(creditCardDto.getPersonalCustomerId())
                    .flatMap(customerDto -> repo.existsByPersonalCustomerId(customerDto.getId())
                            .flatMap(exists -> (!exists) ? registeredCreditCard : Mono.error(new DuplicateCreditException(
                                    String.format(Constants.CREDIT_DUPLICATED_BY_A_FIELD, Constants.PERSONAL_CUSTOMER_ID, customerDto.getId())))));
        } else if (creditCardDto.getBusinessCustomerId() != null) {
            return customerService.getBusinessCustomerById(creditCardDto.getBusinessCustomerId()).flatMap(customer -> registeredCreditCard);
        }
        return Mono.error(new BadRequestException(Constants.CUSTOMER_ID_IS_REQUIRED));
    }

    @Override
    public Mono<CreditCardDto> updateById(String id, CreditCardDto creditCardDto) {
        Mono<CreditCardDto> creditCardDtoReqMono = Mono.just(creditCardDto);
        Mono<CreditCardDto> creditCardDtoDbMono = getById(id);
        return creditCardDtoReqMono.zipWith(creditCardDtoDbMono, (creditDtoReq, creditDtoDb) ->
                        CreditMapper.toModel(creditDtoDb.toBuilder()
                                .cardNumber(creditDtoReq.getCardNumber())
                                .expirationDate(creditDtoReq.getExpirationDate())
                                .cvv(creditDtoReq.getCvv())
                                .creditLine(creditDtoReq.getCreditLine())
                                .personalCustomerId(creditDtoReq.getPersonalCustomerId())
                                .businessCustomerId(creditDtoReq.getBusinessCustomerId())
                                .build()))
                .flatMap(customer -> repo.save(customer).map(CreditMapper::toDto));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return getById(id).flatMap(creditCardDto -> repo.deleteById(id));
    }

}
