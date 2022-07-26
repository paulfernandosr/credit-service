package com.nttdata.creditservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditDto {

    private final String id;

    private final String cardNumber;
    private final String cvv;
    private final Double balance;
    private final Double creditLine;
    private final LocalDate cardExpirationDate;

    private final Double amountToPay;
    private final Double amountPaid;

    private final LocalDate paymentDate;
    private final String type;
    private final String customerId;
    private final CustomerDto customer;
    private final List<MovementDto> movements;

}
