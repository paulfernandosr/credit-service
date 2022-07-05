package com.nttdata.creditservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessCreditDto {

    private String id;

    @NotNull(message = Constants.NOT_NULL)
    private Double amount;

    @NotNull(message = Constants.NOT_NULL)
    private Double interestRate;

    @NotNull(message = Constants.NOT_NULL)
    private String businessCustomerId;

}
