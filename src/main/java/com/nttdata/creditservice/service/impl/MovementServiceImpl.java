package com.nttdata.creditservice.service.impl;

import com.nttdata.creditservice.config.PropertiesConfig;
import com.nttdata.creditservice.dto.MovementDto;
import com.nttdata.creditservice.exception.DomainException;
import com.nttdata.creditservice.service.IMovementService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeoutException;

@Service
public class MovementServiceImpl implements IMovementService {

    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public MovementServiceImpl(WebClient.Builder webClientBuilder, PropertiesConfig propertiesConfig) {
        this.webClient = webClientBuilder.baseUrl(propertiesConfig.movementServiceBaseUrl).build();
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    @CircuitBreaker(name = "movementService", fallbackMethod = "fallbackGetMovementsByCreditId")
    @TimeLimiter(name = "movementService", fallbackMethod = "fallbackGetMovementsByCreditId")
    public Flux<MovementDto> getMovementsByCreditId(String creditId) {
        return webClient.get().uri(propertiesConfig.getMovementsByCreditId, creditId)
                .retrieve()
                .bodyToFlux(MovementDto.class);
    }

    private Flux<MovementDto> fallbackGetMovementsByCreditId(String creditId, WebClientResponseException e) {
        return Flux.error(new DomainException(e.getStatusCode(), e.getMessage()));
    }

    private Flux<MovementDto> fallbackGetMovementsByCreditId(String creditId, TimeoutException e) {
        return Flux.error(new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
