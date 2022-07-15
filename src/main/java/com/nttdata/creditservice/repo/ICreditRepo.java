package com.nttdata.creditservice.repo;

import com.nttdata.creditservice.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditRepo extends ReactiveMongoRepository<Credit, String> {

    Mono<Boolean> existsByCustomerId(String customerId);

    Mono<Boolean> existsByCardNumberOrCustomerId(String cardNumber, String customerId);

    Mono<Boolean> existsByCardNumber(String cardNumber);

    Flux<Credit> findByCustomerId(String customerId);

}
