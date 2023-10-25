package com.crud.invoices.service;


import com.crud.invoices.domain.Address;
import com.crud.invoices.domain.Contractor;
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

    @Autowired
    ContractorService contractorService;

    @Autowired
    AddressService addressService;

    public List<Invoice> getAllInvoices(Pageable pageable) {
        Page<Invoice> bookPage = invoiceRepository.findAll(pageable);
        return bookPage.getContent();


    }
    @Transactional
    public Invoice saveInvoice(final Invoice invoice) {

        Optional<Contractor> contractorInDatabase = contractorService.findContractorByVatIdentificationNumber(invoice.getContractor().getVatIdentificationNumber());
        //contractorInDatabase.ifPresent(contractor -> invoice.setContractor(contractorInDatabase.get()));
         if(contractorInDatabase.isPresent()) {
             invoice.getContractor().setId(contractorInDatabase.get().getId());
             Optional<Address> addressForContractor = addressService.getAddress(contractorInDatabase.get().getAddress().getId());
             if (addressForContractor.isPresent()) {
                 contractorInDatabase.get().getAddress().setStreet(invoice.getContractor().getAddress().getStreet());
                 contractorInDatabase.get().getAddress().setStreetNumber(invoice.getContractor().getAddress().getStreetNumber());
                 contractorInDatabase.get().getAddress().setPostcode(invoice.getContractor().getAddress().getPostcode());
                 contractorInDatabase.get().getAddress().setCity(invoice.getContractor().getAddress().getCity());
                 contractorInDatabase.get().getAddress().setCountry(invoice.getContractor().getAddress().getCountry());
             }
         }
        else {
            addressService.saveAddress(invoice.getContractor().getAddress());
        }


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
        return invoiceRepository.save(invoiceIn);

    }


    @Transactional
    public Invoice saveInvoiceWithoutContractor(final Invoice invoice) {
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
