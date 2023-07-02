package com.crud.invoices.exception;

public class EmailNotFoundException extends  RuntimeException {

    public EmailNotFoundException(String s) {
        super("Could not find user with such email");
    }
}
