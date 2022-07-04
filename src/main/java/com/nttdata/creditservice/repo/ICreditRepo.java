package com.nttdata.creditservice.repo;

import com.nttdata.creditservice.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ICreditRepo extends ReactiveMongoRepository<Credit, String> {

    Mono<Boolean> existsByPersonalCustomerId(String personalCustomerId);

}
