package com.crud.invoices.service;


import com.crud.invoices.domain.Invoice;
import com.crud.invoices.respository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices(Pageable pageable) {
        Page<Invoice> bookPage = invoiceRepository.findAll(pageable);
        return bookPage.getContent();


    }
    @Transactional
    public Invoice saveInvoice(final Invoice invoice) {
        LocalDate dateOfInvoice = invoice.getDateOfInvoice();
        Integer periodOfPayment = invoice.getPeriodOfPayment();
        LocalDate datePayment = dateOfInvoice.plusDays(periodOfPayment);
        invoice.setDateOfPayment(datePayment);
        BigDecimal netAmount = invoice.getNetAmount();
        BigDecimal sumTotal = invoice.getSumTotal();
        BigDecimal vatAmount = sumTotal.subtract(netAmount);
        invoice.setAmountOfVAT(vatAmount);
        BigDecimal paid = invoice.getPaid();
        BigDecimal leftToPay = sumTotal.subtract(paid);
        invoice.setLeftToPay(leftToPay);
        Invoice invoiceWithId = invoiceRepository.save(invoice);
        Invoice invoiceIn = invoiceRepository.findById(invoiceWithId.getId()).get();

        int year = invoiceIn.getDateOfInvoice().getYear();
        StringBuilder sb = new StringBuilder("FV ");
        sb.append(invoiceIn.getId().toString());
        sb.append("/");
        String number = String.valueOf(sb.append(year));

        invoiceIn.setNumber(number);
        System.out.println(number);
        return invoiceRepository.save(invoiceIn);

    }

    public Optional<Invoice> getInvoice(final Long id) {
        return invoiceRepository.findById(id);
    }

    public void deleteInvoice(final Long id) {
        invoiceRepository.deleteById(id);
    }

    public long getCount() {
        return invoiceRepository.count();
    }

}
