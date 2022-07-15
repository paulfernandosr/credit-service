package com.nttdata.creditservice.model;

import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@Document(collection = Constants.CREDITS_COLLECTION)
public class Credit {

    @Id
    private String id;

    private String cardNumber;
    private String cvv;
    private Double balance;
    private Double creditLine;
    private LocalDate expirationDate;

    private Double amountToPay;
    private Double amountPaid;
    private LocalDate paymentDate;

    private String type;
    private String customerId;

}
