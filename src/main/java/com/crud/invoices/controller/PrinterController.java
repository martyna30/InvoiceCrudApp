package com.crud.invoices.controller;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.print.PrinterService;
import com.crud.invoices.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/invoice/printer")
public class PrinterController {

    private final String filePath = "/home/martyna/Pulpit/pdfPlik.plik.pdf";

    @Autowired
    PrinterService printerService;

    @Autowired
    InvoiceService invoiceService;


    @GetMapping(value="generateInvoice")
    public ResponseEntity<?>generateInvoice(@RequestParam Long invoiceId) {
        Optional<Invoice> invoice = invoiceService.getInvoice(invoiceId);
        if(invoice.isPresent()) {
            try {
                this.printerService.generatePDF(invoice.get(), filePath);
                return ResponseEntity.ok(HttpStatus.CREATED);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





}
