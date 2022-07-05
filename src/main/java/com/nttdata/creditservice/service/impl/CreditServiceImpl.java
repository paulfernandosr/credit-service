package com.nttdata.creditservice.service.impl;

import com.nttdata.creditservice.dto.BusinessCreditDto;
import com.nttdata.creditservice.dto.CreditDto;
import com.nttdata.creditservice.dto.PersonalCreditCardDto;
import com.nttdata.creditservice.dto.PersonalCreditDto;
import com.nttdata.creditservice.exception.BadRequestException;
import com.nttdata.creditservice.exception.CreditNotFoundException;
import com.nttdata.creditservice.exception.DuplicateCreditException;
import com.nttdata.creditservice.repo.ICreditRepo;
import com.nttdata.creditservice.service.ICreditService;
import com.nttdata.creditservice.service.ICustomerService;
import com.nttdata.creditservice.util.Constants;
import com.nttdata.creditservice.util.CreditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements ICreditService {

    private final ICreditRepo repo;
    private final ICustomerService customerService;

    @Override
    public Flux<CreditDto> getAll() {
        return repo.findAll().map(CreditMapper::toDto);
    }

    @Override
    public Mono<CreditDto> getById(String id) {
        return repo.findById(id)
                .map(CreditMapper::toDto)
                .switchIfEmpty(Mono.error(new CreditNotFoundException(String.format(Constants.CREDIT_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<CreditDto> register(CreditDto creditDto) {
        Mono<CreditDto> registeredCredit = Mono.just(CreditMapper.toModel(creditDto))
                .flatMap(credit -> repo.save(credit.toBuilder().id(null).build()).map(CreditMapper::toDto));

        if (creditDto.getPersonalCustomerId() != null) {
            return customerService.getPersonalCustomerById(creditDto.getPersonalCustomerId())
                    .flatMap(customerDto -> repo.existsByPersonalCustomerId(customerDto.getId())
                            .flatMap(exists -> (!exists) ? registeredCredit : Mono.error(new DuplicateCreditException(
                                    String.format(Constants.CREDIT_DUPLICATED_BY_A_FIELD, Constants.PERSONAL_CUSTOMER_ID, customerDto.getId())))));
        } else if (creditDto.getBusinessCustomerId() != null) {
            return customerService.getBusinessCustomerById(creditDto.getBusinessCustomerId()).flatMap(customer -> registeredCredit);
        }
        return Mono.error(new BadRequestException(Constants.CUSTOMER_ID_IS_REQUIRED));
    }

    @Override
    public Mono<PersonalCreditDto> registerPersonal(PersonalCreditDto creditDto) {
        return customerService.getPersonalCustomerById(creditDto.getPersonalCustomerId())
                .map(customerDto -> CreditMapper.toModel(creditDto))
                .flatMap(credit -> repo.existsByPersonalCustomerId(credit.getPersonalCustomerId())
                        .flatMap(exists -> {
                            if (Boolean.FALSE.equals(exists)) {
                                return repo.save(credit.toBuilder().id(null).build()).map(CreditMapper::toPersonalDto);
                            } else {
                                PersonalCreditDto duplicateCreditDto = CreditMapper.toPersonalDto(credit);
                                return Mono.error(new DuplicateCreditException(
                                        String.format(Constants.CREDIT_DUPLICATED_BY_A_FIELD,
                                                Constants.PERSONAL_CUSTOMER_ID, duplicateCreditDto.getPersonalCustomerId())));
                            }
                        }));
    }

    @Override
    public Mono<BusinessCreditDto> registerBusiness(BusinessCreditDto creditDto) {
        return customerService.getBusinessCustomerById(creditDto.getBusinessCustomerId())
                .map(customerDto -> CreditMapper.toModel(creditDto))
                .flatMap(credit -> repo.save(credit.toBuilder().id(null).build()).map(CreditMapper::toBusinessDto));
    }

    @Override
    public Mono<CreditDto> updateById(String id, CreditDto creditDto) {
        Mono<CreditDto> accountDtoReqMono = Mono.just(creditDto);
        Mono<CreditDto> accountDtoDbMono = getById(id);
        return accountDtoReqMono.zipWith(accountDtoDbMono, (creditDtoReq, creditDtoDb) ->
                        CreditMapper.toModel(creditDtoDb.toBuilder()
                                .amount(creditDtoReq.getAmount())
                                .interestRate(creditDtoReq.getInterestRate())
                                .personalCustomerId(creditDtoReq.getPersonalCustomerId())
                                .businessCustomerId(creditDtoReq.getBusinessCustomerId())
                                .build()))
                .flatMap(customer -> repo.save(customer).map(CreditMapper::toDto));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return getById(id).flatMap(creditDto -> repo.deleteById(id));
    }

}
