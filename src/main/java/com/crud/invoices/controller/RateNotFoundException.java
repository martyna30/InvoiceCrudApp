package com.crud.invoices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Rate not found")
public class RateNotFoundException extends Exception {

}
