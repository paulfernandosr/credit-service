package com.nttdata.creditservice.service.impl;

import com.nttdata.creditservice.dto.BusinessCreditCardDto;
import com.nttdata.creditservice.dto.CreditCardDto;
import com.nttdata.creditservice.dto.PersonalCreditCardDto;
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
    public Mono<PersonalCreditCardDto> registerPersonal(PersonalCreditCardDto creditCardDto) {
        return customerService.getPersonalCustomerById(creditCardDto.getPersonalCustomerId())
                .map(customerDto -> CreditMapper.toModel(creditCardDto))
                .flatMap(creditCard -> repo.existsByCardNumberOrPersonalCustomerId(
                                creditCard.getCardNumber(),
                                creditCard.getPersonalCustomerId())
                        .flatMap(exists -> {
                            if (Boolean.FALSE.equals(exists)) {
                                return repo.save(creditCard.toBuilder().id(null).build()).map(CreditMapper::toPersonalDto);
                            } else {
                                PersonalCreditCardDto duplicateCreditCardDto = CreditMapper.toPersonalDto(creditCard);
                                return Mono.error(new DuplicateCreditException(
                                        String.format(Constants.CREDIT_DUPLICATED_BY_TWO_FIELDS,
                                                Constants.CARD_NUMBER, duplicateCreditCardDto.getCardNumber(),
                                                Constants.PERSONAL_CUSTOMER_ID, duplicateCreditCardDto.getPersonalCustomerId())));
                            }
                        }));
    }

    @Override
    public Mono<BusinessCreditCardDto> registerBusiness(BusinessCreditCardDto creditCardDto) {
        return customerService.getBusinessCustomerById(creditCardDto.getBusinessCustomerId())
                .map(customerDto -> CreditMapper.toModel(creditCardDto))
                .flatMap(creditCard -> repo.existsByCardNumber(creditCard.getCardNumber())
                        .flatMap(exists -> {
                            if (Boolean.FALSE.equals(exists)) {
                                return repo.save(creditCard.toBuilder().id(null).build()).map(CreditMapper::toBusinessDto);
                            } else {
                                BusinessCreditCardDto duplicateCreditCardDto = CreditMapper.toBusinessDto(creditCard);
                                return Mono.error(new DuplicateCreditException(
                                        String.format(Constants.CREDIT_DUPLICATED_BY_TWO_FIELDS,
                                                Constants.CARD_NUMBER, duplicateCreditCardDto.getCardNumber(),
                                                Constants.BUSINESS_CUSTOMER_ID, duplicateCreditCardDto.getBusinessCustomerId())));
                            }
                        }));
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
