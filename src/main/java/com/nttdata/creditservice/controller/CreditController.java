package com.nttdata.creditservice.controller;

import com.nttdata.creditservice.dto.CreditCardDto;
import com.nttdata.creditservice.dto.CreditDto;
import com.nttdata.creditservice.service.ICreditCardService;
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

    @GetMapping(Constants.GET_BY_ID_METHOD)
    public Mono<ResponseEntity<CreditDto>> getById(@PathVariable(Constants.PATH_ID_VARIABLE) String id) {
        return service.getById(id).map(credit -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credit));
    }

    @PostMapping(Constants.REGISTER_METHOD)
    public Mono<ResponseEntity<CreditDto>> register(@RequestBody CreditDto credit, final ServerHttpRequest request) {
        return validator.validate(credit)
                .flatMap(validatedCredit -> service.register(validatedCredit)
                        .map(registeredCredit -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredCredit.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(registeredCredit)));
    }

    @PutMapping(Constants.UPDATE_BY_ID_METHOD)
    public Mono<ResponseEntity<CreditDto>> updateById(@PathVariable(Constants.PATH_ID_VARIABLE) String id, @RequestBody CreditDto credit) {
        return validator.validate(credit)
                .flatMap(validatedCredit -> service.updateById(id, validatedCredit)
                        .map(updatedCredit -> ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(updatedCredit)));
    }

    @DeleteMapping(Constants.DELETE_BY_ID_METHOD)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable(Constants.PATH_ID_VARIABLE) String id) {
        return service.deleteById(id).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

}
