package com.crud.invoices.controller;


import com.crud.invoices.domain.InvoiceDto;
import com.crud.invoices.domain.ListInvoicesDto;
import com.crud.invoices.mapper.InvoiceMapper;
import com.crud.invoices.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //ResponseEntity zmien
    @GetMapping(value = "getInvoice")
    public InvoiceDto getInvoice(@RequestParam Long invoiceId) throws InvoiceNotFoundException {
        return invoiceMapper.mapToInvoiceDto(invoiceService.getInvoice(invoiceId).orElseThrow(InvoiceNotFoundException::new));
    }

    @DeleteMapping(value = "deleteInvoice", consumes = APPLICATION_JSON_VALUE)
    public void deleteInvoice(@RequestParam Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }

    @PutMapping(value = "updateInvoice")
    public InvoiceDto updateInvoice(@RequestBody InvoiceDto invoiceDto) {
        return invoiceMapper.mapToInvoiceDto(invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto)));
    }

    @PostMapping(value = "createInvoice", consumes = APPLICATION_JSON_VALUE)
    public void createInvoice(@RequestBody InvoiceDto invoiceDto) {
        invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto));
    }
}






