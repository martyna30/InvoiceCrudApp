package com.crud.invoices.controller;

import com.crud.invoices.domain.PaymentDto;
import com.crud.invoices.mapper.PaymentMapper;
import com.crud.invoices.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentMapper paymentMapper;

    @PostMapping(value = "createPayment", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createPayment(@Valid @RequestBody PaymentDto paymentDto,
                                                @RequestParam  Long invoiceId,
                                                Errors errors) {
        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        paymentService.savePayment(paymentMapper.mapToPayment(paymentDto),invoiceId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "getPaymentsByInvoiceId")
    public List<PaymentDto> getPaymentsByInvoice(@RequestParam Long invoiceId) {
        return paymentMapper.mapToPaymentsDto(paymentService.findPaymentsByInvoiceId(invoiceId));

    }

    @DeleteMapping(value = "deleteInvoice", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteInvoice(@RequestParam Long paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
