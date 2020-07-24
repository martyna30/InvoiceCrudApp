package com.crud.invoices.controller;


import com.crud.invoices.domain.InvoiceDto;
import com.crud.invoices.mapper.InvoiceMapper;
import com.crud.invoices.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceMapper invoiceMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getInvoices")
    public List<InvoiceDto> getInvoice() {
        return invoiceMapper.mapToInvoiceDtoList(invoiceService.getAllInvoices());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getInvoice")
    public InvoiceDto getInvoice(@RequestParam Long invoiceId) throws InvoiceNotFoundException {
        return invoiceMapper.mapToInvoiceDto(invoiceService.getInvoice(invoiceId).orElseThrow(InvoiceNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteInvoice", consumes = APPLICATION_JSON_VALUE )
    public void deleteInvoice(@RequestParam Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateInvoice")
    public InvoiceDto updateInvoice(@RequestBody InvoiceDto invoiceDto) {
        return invoiceMapper.mapToInvoiceDto(invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createInvoice", consumes = APPLICATION_JSON_VALUE)
    public void createInvoice(@RequestBody InvoiceDto invoiceDto) {
        invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto));
    }
}






