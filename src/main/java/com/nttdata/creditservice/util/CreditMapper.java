package com.nttdata.creditservice.util;

import com.nttdata.creditservice.dto.CreditCardDto;
import com.nttdata.creditservice.dto.CreditDto;
import com.nttdata.creditservice.model.Credit;
import com.nttdata.creditservice.model.CreditCard;

public class CreditMapper {

    private CreditMapper() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static Credit toModel(CreditDto creditDto) {
        return Credit.builder()
                .id(creditDto.getId())
                .amount(creditDto.getAmount())
                .interestRate(creditDto.getInterestRate())
                .personalCustomerId(creditDto.getPersonalCustomerId())
                .businessCustomerId(creditDto.getBusinessCustomerId())
                .build();
    }

    public static CreditDto toDto(Credit credit) {
        return CreditDto.builder()
                .id(credit.getId())
                .amount(credit.getAmount())
                .interestRate(credit.getInterestRate())
                .personalCustomerId(credit.getPersonalCustomerId())
                .businessCustomerId(credit.getBusinessCustomerId())
                .build();
    }

    public static CreditCard toModel(CreditCardDto creditCardDto) {
        return CreditCard.builder()
                .id(creditCardDto.getId())
                .cardNumber(creditCardDto.getCardNumber())
                .expirationDate(creditCardDto.getExpirationDate())
                .cvv(creditCardDto.getCvv())
                .creditLine(creditCardDto.getCreditLine())
                .personalCustomerId(creditCardDto.getPersonalCustomerId())
                .businessCustomerId(creditCardDto.getBusinessCustomerId())
                .build();
    }

    public static CreditCardDto toDto(CreditCard creditCard) {
        return CreditCardDto.builder()
                .id(creditCard.getId())
                .cardNumber(creditCard.getCardNumber())
                .expirationDate(creditCard.getExpirationDate())
                .cvv(creditCard.getCvv())
                .creditLine(creditCard.getCreditLine())
                .personalCustomerId(creditCard.getPersonalCustomerId())
                .businessCustomerId(creditCard.getBusinessCustomerId())
                .build();
    }

}
