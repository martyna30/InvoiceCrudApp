package com.crud.invoices.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NullPointerExceptionAdvice {
   @ResponseBody
   @ExceptionHandler(InvoiceNullPointerException.class)
   //@ResponseStatus(HttpStatus.FORBIDDEN)
   public String getResponse(InvoiceNullPointerException ex) {
        return ex.getMessage();
   }

}
