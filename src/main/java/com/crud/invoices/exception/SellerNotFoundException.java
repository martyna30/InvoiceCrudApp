package com.crud.invoices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "seller not found")
public class SellerNotFoundException  extends RuntimeException{
    public SellerNotFoundException(String s) {
        super("Could not find seller with such a name");
    }
}
