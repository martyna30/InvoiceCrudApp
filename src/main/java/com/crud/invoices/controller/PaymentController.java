package com.crud.invoices.controller;

import com.crud.invoices.domain.PaymentDto;
import com.crud.invoices.exception.ExceptionHandlerController;
import com.crud.invoices.mapper.PaymentMapper;
import com.crud.invoices.service.PaymentService;
import com.crud.invoices.validationGroup.OrderChecks4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
    @Autowired
    ExceptionHandlerController exceptionHandlerController;

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentMapper paymentMapper;

    @Autowired
    ValidationErrors validationErrors;

    @PostMapping(value = "createPayment", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createPayment(@Validated(value = {OrderChecks4.class})
                                                    @Valid @RequestBody PaymentDto paymentDto, @RequestParam  Long invoiceId,
                                             Errors errors) throws MethodArgumentNotValidException {
        if (errors.hasErrors()) {
            return validationErrors.checkErrors(errors);
        }
        paymentService.savePayment(paymentMapper.mapToPayment(paymentDto),invoiceId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "settlePayment", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> settlePayment(@Validated(value = {OrderChecks4.class})
                                                @Valid @RequestBody PaymentDto paymentDto, @RequestParam  Long invoiceId,
                                                Errors errors) throws MethodArgumentNotValidException {
        if (errors.hasErrors()) {
            return validationErrors.checkErrors(errors);
        }
        paymentService.settle(paymentMapper.mapToPayment(paymentDto),invoiceId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(value = "getPaymentsByInvoiceId")
    public List<PaymentDto> getPaymentsByInvoice(@RequestParam Long invoiceId) {
        return paymentMapper.mapToPaymentsDto(paymentService.findPaymentsByInvoiceId(invoiceId));

    }

    @DeleteMapping(value = "deletePayment", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePayment(@RequestParam Long paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
