package com.nttdata.creditservice.service.impl;

import com.nttdata.creditservice.config.PropertiesConfig;
import com.nttdata.creditservice.dto.BusinessCustomerDto;
import com.nttdata.creditservice.dto.ErrorResponseBodyDto;
import com.nttdata.creditservice.dto.PersonalCustomerDto;
import com.nttdata.creditservice.exception.DomainException;
import com.nttdata.creditservice.service.ICustomerService;
import com.nttdata.creditservice.util.Constants;
import com.nttdata.creditservice.util.JacksonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public CustomerServiceImpl(WebClient.Builder webClientBuilder, PropertiesConfig propertiesConfig) {
        this.webClient = webClientBuilder.baseUrl(propertiesConfig.customerServiceBaseUrl).build();
        this.propertiesConfig = propertiesConfig;
    }

    public Mono<PersonalCustomerDto> getPersonalCustomerById(String id) {
        return webClient.get().uri(propertiesConfig.getPersonalCustomerByIdMethod, id).retrieve()
                .bodyToMono(PersonalCustomerDto.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new DomainException(e.getStatusCode(), String.format(Constants.ERROR_RESPONSE_IN_SERVICE,
                                e.getMessage(), JacksonUtil.jsonStringToObject(e.getResponseBodyAsString(), ErrorResponseBodyDto.class).getMessage()))));
    }

    public Mono<BusinessCustomerDto> getBusinessCustomerById(String id) {
        return webClient.get().uri(propertiesConfig.getBusinessCustomerByIdMethod, id).retrieve()
                .bodyToMono(BusinessCustomerDto.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new DomainException(e.getStatusCode(), String.format(Constants.ERROR_RESPONSE_IN_SERVICE,
                                e.getMessage(), JacksonUtil.jsonStringToObject(e.getResponseBodyAsString(), ErrorResponseBodyDto.class).getMessage()))));
    }

}
