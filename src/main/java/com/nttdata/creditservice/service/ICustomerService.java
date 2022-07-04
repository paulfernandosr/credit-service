package com.nttdata.creditservice.service;

import com.nttdata.creditservice.dto.BusinessCustomerDto;
import com.nttdata.creditservice.dto.PersonalCustomerDto;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<PersonalCustomerDto> getPersonalCustomerById(String id);

    Mono<BusinessCustomerDto> getBusinessCustomerById(String id);

}
