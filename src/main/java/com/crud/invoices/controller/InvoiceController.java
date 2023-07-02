package com.crud.invoices.controller;


import com.crud.invoices.domain.InvoiceDto;
import com.crud.invoices.domain.ListInvoicesDto;
import com.crud.invoices.mapper.InvoiceMapper;
import com.crud.invoices.service.InvoiceService;
import com.crud.invoices.validationGroup.OrderChecks2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceMapper invoiceMapper;

    @GetMapping(value = "getInvoices")
    public ResponseEntity<ListInvoicesDto> getInvoices(@RequestParam int page, @RequestParam int size) throws ResponseStatusException {
        PageRequest pageRequest = PageRequest.of(page, size);
        try {
            return ResponseEntity.ok(new ListInvoicesDto(invoiceService.getCount(),
                    invoiceMapper.mapToInvoiceDtoList(invoiceService.getAllInvoices(pageRequest))));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @GetMapping(value = "getInvoice")
    public ResponseEntity<InvoiceDto>getInvoice(@RequestParam Long invoiceId) {
        try {
            return ResponseEntity.ok(invoiceMapper.mapToInvoiceDto(invoiceService.getInvoice(invoiceId).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "deleteInvoice", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteInvoice(@RequestParam Long invoiceId) {
        try {
            invoiceService.deleteInvoice(invoiceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "updateInvoice")
    public ResponseEntity<Object> updateInvoice(@Validated(value = {OrderChecks2.class}) @Valid @RequestBody InvoiceDto invoiceDto, Errors errors) {

        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>>errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if(!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        invoiceMapper.mapToInvoiceDto(invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "createInvoice", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createInvoice(@Validated(value = {OrderChecks2.class}) @Valid @RequestBody InvoiceDto invoiceDto, Errors errors) {
        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>>errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if(!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}






