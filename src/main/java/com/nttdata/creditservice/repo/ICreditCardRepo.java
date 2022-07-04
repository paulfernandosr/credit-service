package com.nttdata.creditservice.repo;

import com.nttdata.creditservice.model.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ICreditCardRepo extends ReactiveMongoRepository<CreditCard, String> {

    Mono<Boolean> existsByCardNumber(String cardNumber);

    Mono<Boolean> existsByPersonalCustomerId(String personalCustomerId);

}
