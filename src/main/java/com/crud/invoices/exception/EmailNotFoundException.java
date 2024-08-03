package com.crud.invoices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends  RuntimeException {

    public EmailNotFoundException(String s) {
        super("Could not find user with such email");
    }
}
