package com.nttdata.creditservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardDto {

    private String id;

    @NotNull(message = Constants.NOT_NULL)
    private String cardNumber;

    @NotNull(message = Constants.NOT_NULL)
    private String expirationDate;

    @NotNull(message = Constants.NOT_NULL)
    private String cvv;

    @NotNull(message = Constants.NOT_NULL)
    private String creditLine;

    private String personalCustomerId;
    private String businessCustomerId;

    @AssertTrue(message = Constants.CUSTOMER_ID_IS_REQUIRED)
    private boolean isPersonalCustomerId() {
        return isPersonalCustomerIdOrBusinessCustomerId();
    }

    @AssertTrue(message = Constants.CUSTOMER_ID_IS_REQUIRED)
    private boolean isBusinessCustomerId() {
        return isPersonalCustomerIdOrBusinessCustomerId();
    }

    private boolean isPersonalCustomerIdOrBusinessCustomerId() {
        return (personalCustomerId != null && businessCustomerId == null)
                || (personalCustomerId == null && businessCustomerId != null);
    }

}
