package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<CustomerDto> getCustomerById(String id);

}
