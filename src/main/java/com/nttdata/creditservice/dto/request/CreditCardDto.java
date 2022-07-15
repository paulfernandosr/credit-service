package com.nttdata.creditservice.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardDto {

    @NotNull(message = Constants.NOT_NULL)
    private String cardNumber;

    @NotNull(message = Constants.NOT_NULL)
    private String cvv;

    @NotNull(message = Constants.NOT_NULL)
    private LocalDate expirationDate;

    private Double balance;

    @NotNull(message = Constants.NOT_NULL)
    @Min(value = 0, message = Constants.LESS_THAN_ZERO)
    private Double creditLine;

    @NotNull(message = Constants.NOT_NULL)
    private String customerId;

}
