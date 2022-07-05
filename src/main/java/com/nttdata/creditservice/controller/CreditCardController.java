package com.nttdata.creditservice.controller;

import com.nttdata.creditservice.dto.BusinessCreditCardDto;
import com.nttdata.creditservice.dto.CreditCardDto;
import com.nttdata.creditservice.dto.PersonalCreditCardDto;
import com.nttdata.creditservice.service.ICreditCardService;
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
@RequestMapping(Constants.CREDIT_CARD_CONTROLLER)
public class CreditCardController {

    private final ICreditCardService service;
    private final RequestValidator validator;

    @GetMapping(Constants.GET_ALL_METHOD)
    public Mono<ResponseEntity<Flux<CreditCardDto>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @GetMapping(Constants.GET_BY_ID_METHOD)
    public Mono<ResponseEntity<CreditCardDto>> getById(@PathVariable(Constants.PATH_ID_VARIABLE) String id) {
        return service.getById(id).map(creditCard -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(creditCard));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_CREDIT_CARD_METHOD)
    public Mono<ResponseEntity<PersonalCreditCardDto>> registerPersonal(@RequestBody PersonalCreditCardDto creditCard, final ServerHttpRequest request) {
        return validator.validate(creditCard)
                .flatMap(validatedCreditCard -> service.registerPersonal(validatedCreditCard)
                        .map(registeredCreditCard -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredCreditCard.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(registeredCreditCard)));
    }

    @PostMapping(Constants.REGISTER_BUSINESS_CREDIT_CARD_METHOD)
    public Mono<ResponseEntity<BusinessCreditCardDto>> registerBusiness(@RequestBody BusinessCreditCardDto creditCard, final ServerHttpRequest request) {
        return validator.validate(creditCard)
                .flatMap(validatedCreditCard -> service.registerBusiness(validatedCreditCard)
                        .map(registeredCreditCard -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredCreditCard.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(registeredCreditCard)));
    }

    @PutMapping(Constants.UPDATE_BY_ID_METHOD)
    public Mono<ResponseEntity<CreditCardDto>> updateById(@PathVariable(Constants.PATH_ID_VARIABLE) String id, @RequestBody CreditCardDto creditCard) {
        return validator.validate(creditCard)
                .flatMap(validatedCreditCard -> service.updateById(id, validatedCreditCard)
                        .map(updatedCreditCard -> ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(updatedCreditCard)));
    }

    @DeleteMapping(Constants.DELETE_BY_ID_METHOD)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable(Constants.PATH_ID_VARIABLE) String id) {
        return service.deleteById(id).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

}
