package com.crud.invoices.service;


import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceDto;
import com.crud.invoices.respository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();

    }
    public Invoice saveInvoice(final Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> getInvoice(final Long id) {
        return invoiceRepository.findById(id);
    }

    public void deleteInvoice(final Long id) {
        invoiceRepository.deleteById(id);
    }
}
