package com.nttdata.creditservice.exception;

import org.springframework.http.HttpStatus;

public class DuplicateCreditException extends DomainException {

    public DuplicateCreditException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
