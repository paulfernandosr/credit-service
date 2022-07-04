package com.nttdata.creditservice.exception;

import org.springframework.http.HttpStatus;

public class CreditNotFoundException extends DomainException {

    public CreditNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
