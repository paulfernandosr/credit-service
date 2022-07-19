package com.nttdata.creditservice.service.impl;

import com.nttdata.creditservice.dto.CreditDto;
import com.nttdata.creditservice.dto.MovementDto;
import com.nttdata.creditservice.dto.request.BusinessCreditDto;
import com.nttdata.creditservice.dto.request.CreditCardDto;
import com.nttdata.creditservice.dto.CustomerDto;
import com.nttdata.creditservice.dto.request.GenerateReportDto;
import com.nttdata.creditservice.dto.request.PersonalCreditDto;
import com.nttdata.creditservice.exception.CreditNotFoundException;
import com.nttdata.creditservice.exception.DomainException;
import com.nttdata.creditservice.exception.DuplicateCreditException;
import com.nttdata.creditservice.model.Credit;
import com.nttdata.creditservice.repo.ICreditRepo;
import com.nttdata.creditservice.service.ICreditService;
import com.nttdata.creditservice.service.ICustomerService;
import com.nttdata.creditservice.service.IMovementService;
import com.nttdata.creditservice.util.Constants;
import com.nttdata.creditservice.util.CreditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements ICreditService {

    private final ICreditRepo repo;
    private final ICustomerService customerService;
    private final IMovementService movementService;

    @Override
    public Flux<CreditDto> getAll() {
        return repo.findAll().map(CreditMapper::toCreditDto);
    }

    @Override
    public Flux<CreditDto> getByCustomerId(String customerId) {
        return repo.findByCustomerId(customerId).map(CreditMapper::toCreditDto);
    }

    @Override
    public Mono<CreditDto> getById(String id) {
        return repo.findById(id)
                .map(CreditMapper::toCreditDto)
                .switchIfEmpty(Mono.error(new CreditNotFoundException(String.format(Constants.CREDIT_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<CreditDto> registerPersonalCredit(PersonalCreditDto personalCreditDto) {
        return customerService.getCustomerById(personalCreditDto.getCustomerId())
                .filter(customerDto -> Constants.PERSONAL_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(personalCreditDto.getCustomerId()))
                .flatMap(customerDto -> this.getByCustomerId(personalCreditDto.getCustomerId()).collectList())
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(personalCreditDto.getCustomerId()))
                .map(customerDto -> CreditMapper.toCredit(personalCreditDto))
                .filterWhen(credit -> repo.existsByCustomerId(credit.getCustomerId()).map(exists -> !exists))
                .switchIfEmpty(throwDuplicateCreditException(personalCreditDto.getCustomerId()))
                .map(credit -> credit.toBuilder().amountPaid(0.0).type(Constants.PERSONAL_CREDIT).build())
                .flatMap(repo::save)
                .map(CreditMapper::toCreditDto);
    }

    @Override
    public Mono<CreditDto> registerBusinessCredit(BusinessCreditDto businessCreditDto) {
        return customerService.getCustomerById(businessCreditDto.getCustomerId())
                .filter(customerDto -> Constants.BUSINESS_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(businessCreditDto.getCustomerId()))
                .flatMap(customerDto -> this.getByCustomerId(businessCreditDto.getCustomerId()).collectList())
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(businessCreditDto.getCustomerId()))
                .map(customerDto -> CreditMapper.toCredit(businessCreditDto).toBuilder().amountPaid(0.0).type(Constants.BUSINESS_CREDIT).build())
                .flatMap(repo::save)
                .map(CreditMapper::toCreditDto);
    }

    @Override
    public Mono<CreditDto> registerPersonalCreditCard(CreditCardDto creditCardDto) {
        return customerService.getCustomerById(creditCardDto.getCustomerId())
                .filter(customerDto -> Constants.PERSONAL_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(creditCardDto.getCustomerId()))
                .flatMap(customerDto -> this.getByCustomerId(creditCardDto.getCustomerId()).collectList())
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(creditCardDto.getCustomerId()))
                .map(customerDto -> CreditMapper.toCredit(creditCardDto))
                .filterWhen(credit -> repo.existsByCardNumber(credit.getCardNumber()).map(exists -> !exists))
                .switchIfEmpty(throwDuplicateCreditCardException(creditCardDto.getCardNumber()))
                .map(credit -> credit.toBuilder().balance(credit.getCreditLine()).type(Constants.PERSONAL_CREDIT_CARD).build())
                .flatMap(repo::save)
                .map(CreditMapper::toCreditDto);
    }

    @Override
    public Mono<CreditDto> registerBusinessCreditCard(CreditCardDto creditCardDto) {
        return customerService.getCustomerById(creditCardDto.getCustomerId())
                .filter(customerDto -> Constants.BUSINESS_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(creditCardDto.getCustomerId()))
                .flatMap(customerDto -> this.getByCustomerId(creditCardDto.getCustomerId()).collectList())
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(creditCardDto.getCustomerId()))
                .map(customerDto -> CreditMapper.toCredit(creditCardDto))
                .filterWhen(credit -> repo.existsByCardNumber(credit.getCardNumber()).map(exists -> !exists))
                .switchIfEmpty(throwDuplicateCreditCardException(creditCardDto.getCardNumber()))
                .map(credit -> credit.toBuilder().balance(credit.getCreditLine()).type(Constants.BUSINESS_CREDIT_CARD).build())
                .flatMap(repo::save)
                .map(CreditMapper::toCreditDto);
    }

    @Override
    public Mono<CreditDto> updateById(String id, CreditDto creditDto) {
        return getById(id)
                .map(existingCreditDto -> updateCreditFields(existingCreditDto, creditDto))
                .map(CreditMapper::toCredit)
                .flatMap(repo::save)
                .map(CreditMapper::toCreditDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return getById(id).flatMap(creditCardDto -> repo.deleteById(id));
    }

    @Override
    public Mono<CreditDto> generateReportById(GenerateReportDto generateReportDto) {
        return this.getById(generateReportDto.getCreditId())
                .flatMap(credit -> Mono.zip(Mono.just(credit),
                        customerService.getCustomerById(credit.getCustomerId()),
                        this.getMovementsBetweenDates(generateReportDto).collectList()))
                .map(this::buildReport);
    }

    private Flux<MovementDto> getMovementsBetweenDates(GenerateReportDto generateReportDto) {
        return movementService.getMovementsByCreditId(generateReportDto.getCreditId())
                .filter(movement -> movement.getTimestamp().isBefore(generateReportDto.getEndDate().atStartOfDay())
                        && movement.getTimestamp().isAfter(generateReportDto.getStartDate().atStartOfDay()));
    }

    private CreditDto buildReport(Tuple3<CreditDto, CustomerDto, List<MovementDto>> tuple) {
        return tuple.getT1().toBuilder()
                .customerId(null)
                .customer(tuple.getT2())
                .movements(tuple.getT3())
                .build();
    }

    private boolean isOverdueDebt(CreditDto credit) {
        return (credit.getType().startsWith(Constants.CREDIT)
                && credit.getAmountPaid() < credit.getAmountToPay()
                && credit.getPaymentDate().isBefore(LocalDate.now()))
                || (credit.getType().startsWith(Constants.CREDIT_CARD))
                && credit.getBalance() < credit.getCreditLine()
                && credit.getPaymentDate().isBefore(LocalDate.now());
    }

    private CreditDto updateCreditFields(CreditDto existingCredit, CreditDto modifiedCredit) {
        return existingCredit.toBuilder()
                .cardNumber(modifiedCredit.getCardNumber())
                .cvv(modifiedCredit.getCvv())
                .cardExpirationDate(modifiedCredit.getCardExpirationDate())
                .balance(modifiedCredit.getBalance())
                .creditLine(modifiedCredit.getCreditLine())
                .amountToPay(modifiedCredit.getAmountToPay())
                .paymentDate(modifiedCredit.getPaymentDate())
                .type(modifiedCredit.getType())
                .customerId(modifiedCredit.getCustomerId())
                .build();
    }

    private Mono<Credit> throwDuplicateCreditException(String customerId) {
        return Mono.error(new DuplicateCreditException(
                String.format(Constants.CREDIT_DUPLICATED_BY_A_FIELD,
                        Constants.CUSTOMER_ID, customerId)));
    }

    private Mono<Credit> throwDuplicateCreditCardException(String cardNumber) {
        return Mono.error(new DuplicateCreditException(
                String.format(Constants.CREDIT_CARD_DUPLICATED_BY_A_FIELD,
                        Constants.CARD_NUMBER, cardNumber)));
    }

    private Mono<CustomerDto> throwInvalidCustomerTypeException(String customerId) {
        return Mono.error(new DomainException(HttpStatus.BAD_REQUEST,
                String.format(Constants.INVALID_CUSTOMER_TYPE,
                        Constants.CUSTOMER_ID, customerId)));
    }

    private Mono<List<CreditDto>> throwCustomerWithOverdueDebt(String customerId) {
        return Mono.error(new DomainException(HttpStatus.BAD_REQUEST,
                String.format(Constants.CUSTOMER_WITH_OVERDUE_DEBT,
                        Constants.CUSTOMER_ID, customerId)));
    }

}
