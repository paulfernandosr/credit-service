package com.nttdata.creditservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalCreditCardDto {

    private String id;

    @NotNull(message = Constants.NOT_NULL)
    private String cardNumber;

    @NotNull(message = Constants.NOT_NULL)
    private LocalDate expirationDate;

    @NotNull(message = Constants.NOT_NULL)
    private String cvv;

    @NotNull(message = Constants.NOT_NULL)
    private Double creditLine;

    @NotNull(message = Constants.NOT_NULL)
    private String personalCustomerId;

}
