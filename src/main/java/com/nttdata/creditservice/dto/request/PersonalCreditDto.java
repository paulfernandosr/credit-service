package com.nttdata.creditservice.dto.request;

import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
public class PersonalCreditDto {

    @NotNull(message = Constants.NOT_NULL)
    private Double amountToPay;

    private final Double amountPaid;

    @NotNull(message = Constants.NOT_NULL)
    private LocalDate paymentDate;

    @NotNull(message = Constants.NOT_NULL)
    private String customerId;

}
