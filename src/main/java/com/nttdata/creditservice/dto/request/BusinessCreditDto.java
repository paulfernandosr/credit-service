package com.nttdata.creditservice.dto.request;

import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
public class BusinessCreditDto {

    @NotNull(message = Constants.NOT_NULL)
    private final Double amountToPay;

    private final Double amountPaid;

    @NotNull(message = Constants.NOT_NULL)
    private final LocalDate paymentDate;

    @NotNull(message = Constants.NOT_NULL)
    private final String customerId;

}
