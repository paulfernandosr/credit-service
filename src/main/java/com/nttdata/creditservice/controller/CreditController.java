package com.nttdata.creditservice.controller;

import com.nttdata.creditservice.dto.CreditDto;
import com.nttdata.creditservice.dto.request.BusinessCreditDto;
import com.nttdata.creditservice.dto.request.CreditCardDto;
import com.nttdata.creditservice.dto.request.PersonalCreditDto;
import com.nttdata.creditservice.service.ICreditService;
import com.nttdata.creditservice.util.Constants;
import com.nttdata.creditservice.util.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.CREDIT_CONTROLLER)
public class CreditController {

    private final ICreditService service;
    private final RequestValidator validator;

    @GetMapping(Constants.GET_ALL_METHOD)
    public Mono<ResponseEntity<Flux<CreditDto>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @GetMapping(Constants.GET_BY_CUSTOMER_ID_METHOD)
    public Mono<ResponseEntity<Flux<CreditDto>>> getByCustomerId(@PathVariable(Constants.CUSTOMER_ID) String customerId) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getByCustomerId(customerId)));
    }

    @GetMapping(Constants.GET_BY_ID_METHOD)
    public Mono<ResponseEntity<CreditDto>> getById(@PathVariable(Constants.ID) String id) {
        return service.getById(id).map(credit -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credit));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_CREDIT_METHOD)
    public Mono<ResponseEntity<CreditDto>> registerPersonalCredit(@RequestBody PersonalCreditDto credit, final ServerHttpRequest request) {
        return validator.validate(credit)
                .flatMap(service::registerPersonalCredit)
                .map(registeredCredit -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredCredit.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredCredit));
    }

    @PostMapping(Constants.REGISTER_BUSINESS_CREDIT_METHOD)
    public Mono<ResponseEntity<CreditDto>> registerBusinessCredit(@RequestBody BusinessCreditDto credit, final ServerHttpRequest request) {
        return validator.validate(credit)
                .flatMap(service::registerBusinessCredit)
                .map(registeredCredit -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredCredit.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredCredit));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_CREDIT_CARD_METHOD)
    public Mono<ResponseEntity<CreditDto>> registerPersonalCreditCard(@RequestBody CreditCardDto credit, final ServerHttpRequest request) {
        return validator.validate(credit)
                .flatMap(service::registerPersonalCreditCard)
                .map(registeredCreditCard -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredCreditCard.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredCreditCard));
    }

    @PostMapping(Constants.REGISTER_BUSINESS_CREDIT_CARD_METHOD)
    public Mono<ResponseEntity<CreditDto>> registerBusinessCreditCard(@RequestBody CreditCardDto creditCard, final ServerHttpRequest request) {
        return validator.validate(creditCard)
                .flatMap(service::registerBusinessCreditCard)
                .map(registeredCreditCard -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredCreditCard.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredCreditCard));
    }

    @PutMapping(Constants.UPDATE_BY_ID_METHOD)
    public Mono<ResponseEntity<CreditDto>> updateById(@PathVariable(Constants.ID) String id, @RequestBody CreditDto credit) {
        return validator.validate(credit)
                .flatMap(validatedCredit -> service.updateById(id, validatedCredit)
                        .map(updatedCredit -> ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(updatedCredit)));
    }

    @DeleteMapping(Constants.DELETE_BY_ID_METHOD)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable(Constants.ID) String id) {
        return service.deleteById(id).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

}
