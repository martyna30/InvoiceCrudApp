package com.crud.invoices.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    CommonErrorHandler commonErrorHandler;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<?>  handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errorsMap = new HashMap<>();
      ex.getBindingResult().getAllErrors().stream().forEachOrdered((error) -> {
          String fieldName = ((FieldError)error).getField();
          String errorDefaultMessage = error.getDefaultMessage();
          errorsMap.put(fieldName,errorDefaultMessage);
      });
        errorsMap.values().stream().findFirst();
        //return commonErrorHandler.fieldErrorResponse("Error", commonErrorHandler.getFieldErrorResponse(errorsMap);
        return new ResponseEntity<>(errorsMap,HttpStatus.UNPROCESSABLE_ENTITY );
    }




    /*@ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<?> invalidDataException(InvalidDataException ex, WebRequest request) {

        List<FieldError> errors = ex.getResult().getFieldErrors();
        for (FieldError error : errors) {
            System.out.println("Filed Name ::: " + error.getField() + "Error Message :::" + error.getDefaultMessage());
        }
        return commonErrorHandler.fieldErrorResponse("Error", commonErrorHandler.getFieldErrorResponse(ex.getResult()));
    }*/
}


