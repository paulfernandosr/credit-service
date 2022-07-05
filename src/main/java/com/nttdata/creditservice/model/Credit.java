package com.nttdata.creditservice.model;

import com.nttdata.creditservice.util.Constants;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder(toBuilder = true)
@Document(collection = Constants.CREDITS_COLLECTION)
public class Credit {

    @Id
    private String id;
    private Double amount;
    private Double interestRate;
    private String personalCustomerId;
    private String businessCustomerId;

}
