package com.nttdata.creditservice.util;

import com.nttdata.creditservice.dto.*;
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

    public static Credit toModel(PersonalCreditDto creditDto) {
        return Credit.builder()
                .id(creditDto.getId())
                .amount(creditDto.getAmount())
                .interestRate(creditDto.getInterestRate())
                .personalCustomerId(creditDto.getPersonalCustomerId())
                .build();
    }

    public static Credit toModel(BusinessCreditDto creditDto) {
        return Credit.builder()
                .id(creditDto.getId())
                .amount(creditDto.getAmount())
                .interestRate(creditDto.getInterestRate())
                .businessCustomerId(creditDto.getBusinessCustomerId())
                .build();
    }

    public static PersonalCreditDto toPersonalDto(Credit credit) {
        return PersonalCreditDto.builder()
                .id(credit.getId())
                .amount(credit.getAmount())
                .interestRate(credit.getInterestRate())
                .personalCustomerId(credit.getPersonalCustomerId())
                .build();
    }

    public static BusinessCreditDto toBusinessDto(Credit credit) {
        return BusinessCreditDto.builder()
                .id(credit.getId())
                .amount(credit.getAmount())
                .interestRate(credit.getInterestRate())
                .businessCustomerId(credit.getBusinessCustomerId())
                .build();
    }

    public static CreditCard toModel(CreditCardDto creditCardDto) {
        return CreditCard.builder()
                .id(creditCardDto.getId())
                .cardNumber(creditCardDto.getCardNumber())
                .cvv(creditCardDto.getCvv())
                .expirationDate(creditCardDto.getExpirationDate())
                .consumed(creditCardDto.getConsumed())
                .creditLine(creditCardDto.getCreditLine())
                .personalCustomerId(creditCardDto.getPersonalCustomerId())
                .businessCustomerId(creditCardDto.getBusinessCustomerId())
                .build();
    }

    public static CreditCardDto toDto(CreditCard creditCard) {
        return CreditCardDto.builder()
                .id(creditCard.getId())
                .cardNumber(creditCard.getCardNumber())
                .cvv(creditCard.getCvv())
                .expirationDate(creditCard.getExpirationDate())
                .consumed(creditCard.getConsumed())
                .creditLine(creditCard.getCreditLine())
                .personalCustomerId(creditCard.getPersonalCustomerId())
                .businessCustomerId(creditCard.getBusinessCustomerId())
                .build();
    }

    public static CreditCard toModel(PersonalCreditCardDto creditCardDto) {
        return CreditCard.builder()
                .id(creditCardDto.getId())
                .cardNumber(creditCardDto.getCardNumber())
                .cvv(creditCardDto.getCvv())
                .expirationDate(creditCardDto.getExpirationDate())
                .consumed(creditCardDto.getConsumed())
                .creditLine(creditCardDto.getCreditLine())
                .personalCustomerId(creditCardDto.getPersonalCustomerId())
                .build();
    }

    public static CreditCard toModel(BusinessCreditCardDto creditCardDto) {
        return CreditCard.builder()
                .id(creditCardDto.getId())
                .cardNumber(creditCardDto.getCardNumber())
                .cvv(creditCardDto.getCvv())
                .expirationDate(creditCardDto.getExpirationDate())
                .consumed(creditCardDto.getConsumed())
                .creditLine(creditCardDto.getCreditLine())
                .businessCustomerId(creditCardDto.getBusinessCustomerId())
                .build();
    }

    public static PersonalCreditCardDto toPersonalDto(CreditCard creditCard) {
        return PersonalCreditCardDto.builder()
                .id(creditCard.getId())
                .cardNumber(creditCard.getCardNumber())
                .cvv(creditCard.getCvv())
                .expirationDate(creditCard.getExpirationDate())
                .consumed(creditCard.getConsumed())
                .creditLine(creditCard.getCreditLine())
                .personalCustomerId(creditCard.getPersonalCustomerId())
                .build();
    }

    public static BusinessCreditCardDto toBusinessDto(CreditCard creditCard) {
        return BusinessCreditCardDto.builder()
                .id(creditCard.getId())
                .cardNumber(creditCard.getCardNumber())
                .cvv(creditCard.getCvv())
                .expirationDate(creditCard.getExpirationDate())
                .consumed(creditCard.getConsumed())
                .creditLine(creditCard.getCreditLine())
                .businessCustomerId(creditCard.getBusinessCustomerId())
                .build();
    }

}
