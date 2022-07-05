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
public class CreditDto {

    private String id;

    @NotNull(message = Constants.NOT_NULL)
    private Double amount;

    @NotNull(message = Constants.NOT_NULL)
    private Double interestRate;

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
