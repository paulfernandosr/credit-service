package com.nttdata.creditservice.util;

import com.nttdata.creditservice.dto.CreditDto;
import com.nttdata.creditservice.dto.request.BusinessCreditDto;
import com.nttdata.creditservice.dto.request.CreditCardDto;
import com.nttdata.creditservice.dto.request.PersonalCreditDto;
import com.nttdata.creditservice.model.Credit;

public class CreditMapper {

    private CreditMapper() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static Credit toCredit(CreditDto creditDto) {
        return Credit.builder()
                .id(creditDto.getId())
                .cardNumber(creditDto.getCardNumber())
                .cvv(creditDto.getCvv())
                .expirationDate(creditDto.getExpirationDate())
                .balance(creditDto.getBalance())
                .creditLine(creditDto.getCreditLine())
                .amountToPay(creditDto.getAmountToPay())
                .paymentDate(creditDto.getPaymentDate())
                .type(creditDto.getType())
                .customerId(creditDto.getCustomerId())
                .build();
    }

    public static CreditDto toCreditDto(Credit credit) {
        return CreditDto.builder()
                .id(credit.getId())
                .cardNumber(credit.getCardNumber())
                .cvv(credit.getCvv())
                .expirationDate(credit.getExpirationDate())
                .balance(credit.getBalance())
                .creditLine(credit.getCreditLine())
                .amountToPay(credit.getAmountToPay())
                .paymentDate(credit.getPaymentDate())
                .type(credit.getType())
                .customerId(credit.getCustomerId())
                .build();
    }

    public static Credit toCredit(CreditCardDto creditCardDto) {
        return Credit.builder()
                .cardNumber(creditCardDto.getCardNumber())
                .cvv(creditCardDto.getCvv())
                .expirationDate(creditCardDto.getExpirationDate())
                .balance(creditCardDto.getCreditLine())
                .creditLine(creditCardDto.getCreditLine())
                .customerId(creditCardDto.getCustomerId())
                .build();
    }

    public static Credit toCredit(PersonalCreditDto personalCreditDto) {
        return Credit.builder()
                .amountToPay(personalCreditDto.getAmountToPay())
                .paymentDate(personalCreditDto.getPaymentDate())
                .customerId(personalCreditDto.getCustomerId())
                .build();
    }

    public static Credit toCredit(BusinessCreditDto businessCreditDto) {
        return Credit.builder()
                .amountToPay(businessCreditDto.getAmountToPay())
                .paymentDate(businessCreditDto.getPaymentDate())
                .customerId(businessCreditDto.getCustomerId())
                .build();
    }

}
