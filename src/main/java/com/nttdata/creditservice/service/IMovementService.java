package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.MovementDto;
import reactor.core.publisher.Flux;

public interface IMovementService {

    Flux<MovementDto> getMovementsByCreditId(String creditId);

}
