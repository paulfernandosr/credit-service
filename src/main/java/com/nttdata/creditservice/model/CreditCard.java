package com.nttdata.creditservice.model;

import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder(toBuilder = true)
@Document(collection = Constants.CREDIT_CARDS_COLLECTION)
public class CreditCard {

    @Id
    private String id;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String creditLine;
    private String personalCustomerId;
    private String businessCustomerId;

}
