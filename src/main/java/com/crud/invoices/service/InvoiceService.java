package com.crud.invoices.service;


import com.crud.invoices.controller.ContractorNotFoundException;
import com.crud.invoices.domain.Contractor;
import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceStatus;
import com.crud.invoices.domain.Payment;
import com.crud.invoices.respository.InvoiceRepository;
import com.crud.invoices.respository.PaymentRepository;
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

    @Autowired
    PaymentService paymentService;

    @Autowired
    ItemService itemService;


    @Autowired
    PaymentRepository paymentRepository;


    public List<Invoice> getAllInvoices(Pageable pageable) {
        Page<Invoice> bookPage = invoiceRepository.findAll(pageable);
        return bookPage.getContent();
    }


    @Transactional
    public Invoice saveInvoiceWithContractor(Invoice invoice) throws ContractorNotFoundException {
        this.saveContractorWithAddress(invoice);
        this.addDateOfPayment(invoice);
        this.calculateAmount(invoice);
        this.addPaymentForInvoice(invoice);
        Invoice invoiceWithId = invoiceRepository.save(invoice);
        this.addNumberForInvoice(invoiceWithId);
        return invoiceRepository.save(invoiceWithId);
    }


    @Transactional
    public Invoice updateInvoiceWithContractor(final Invoice invoice) throws ContractorNotFoundException {
        this.saveContractorWithAddress(invoice);
        //this.updateItemForInvoice(invoice);
        this.addDateOfPayment(invoice);
        this.addNumberForInvoice(invoice);
       //items
        Optional<Invoice> invoiceInDatabase = invoiceRepository.findById(invoice.getId());
        InvoiceStatus isSettled = invoiceInDatabase.get().getIsSettled();
        if (isSettled.equals(InvoiceStatus.FALSE)) {
            this.addPaymentForExistingUnsettledInvoice(invoice);
        } else if (isSettled.equals(InvoiceStatus.TRUE)) {
            //this.addPaymentForInvoice(invoice);
            this.paymentService.addPaymentForExistingSettledInvoice(invoice, invoice.getPaid());//zmiana
        }
        return invoiceRepository.save(invoice);
    }


    /*public Invoice settleInvoiceWithContractor(Invoice invoice, Long invoiceId) {
        //Optional<Invoice> invoiceInDatabase = invoiceRepository.findById(invoiceId);
        //if(invoiceInDatabase.isPresent()){
            invoice.setIsSettled(InvoiceStatus.TRUE);
            this.addPaymentForInvoice(invoice);
            this.addPaymentForExistingSettledInvoice(invoice);
            return invoice;
    }*/





    /*@Transactional
    public void updateItemForInvoice(Invoice invoice) {
        List<Item>itemsForInvoice = itemService.getItemsByInvoice(invoice.getId());
        //List<Item>itemsForInvoice = invoiceRepository.findItemsByInvoiceId(invoice.getId());

        if (itemsForInvoice.size() > 0) {
            IntStream.range(1, itemsForInvoice.size())
                    .forEachOrdered(i -> {
                        if (itemsForInvoice.get(i).getProduct().equals(invoice.getItems().get(i).getProduct())) {
                            invoice.getItems().set(i, itemsForInvoice.get(i));
                        }
                        else {
                            System.out.println("Nowy produkt");
                        }
                    });
        }
    }*/


    @Transactional
    public Invoice saveInvoiceWithoutContractor(final Invoice invoice) {
        return null;
    }


    public void calculateAmount(Invoice invoice) {
        BigDecimal currentPayment = invoice.getPaid();
        invoice.setAmountPaid(currentPayment);
        BigDecimal sumTotal = invoice.getSumTotal();
        BigDecimal leftToPay = sumTotal.subtract(currentPayment);
        invoice.setLeftToPay(leftToPay);
        BigDecimal netAmount = invoice.getNetAmount();
        BigDecimal vatAmount = sumTotal.subtract(netAmount);
        invoice.setAmountOfVAT(vatAmount);
    }


    public void addPaymentForInvoice(Invoice invoice) {
        paymentRepository.save(
                new Payment(
                        null,
                        invoice.getMethodOfPayment(),
                        invoice.getPaid(),
                        invoice.getDateOfInvoice(),
                        invoice));
    }
    private void addPaymentForExistingUnsettledInvoice(Invoice invoice) {
           this.calculateAmount(invoice);
           Optional<Payment> paymentForInvoice = paymentService.
                   findFirstPaymentByInvoiceId(invoice.getId());
           if (paymentForInvoice.isPresent()) {
               paymentForInvoice.get().setMethodOfPayment(invoice.getMethodOfPayment());
               paymentForInvoice.get().setPaid(invoice.getPaid());
           } else {
               this.addPaymentForInvoice(invoice);
           }
    }




    public void addDateOfPayment(Invoice invoice) {
        LocalDate dateOfInvoice = invoice.getDateOfInvoice();
        String periodOfPayment = invoice.getPeriodOfPayment();
        LocalDate datePayment = dateOfInvoice.plusDays(Long.parseLong((periodOfPayment)));
        invoice.setDateOfPayment(datePayment);
    }


    public void addNumberForInvoice(Invoice invoice) {
        //Optional<Invoice> invoiceInDatabase = invoiceRepository.findById(invoiceWithId.getId());
        int year = invoice.getDateOfInvoice().getYear();
        StringBuilder sb = new StringBuilder("FV ");
        sb.append(invoice.getId().toString());
        sb.append("/");
        String number = String.valueOf(sb.append(year));

        invoice.setNumber(number);
    }

    @Transactional
    public void saveContractorWithAddress(Invoice invoice) throws ContractorNotFoundException {
        Optional<Contractor> contractorInDatabase =
                contractorService.findContractorByVatIdentificationNumber(invoice.getContractor().getVatIdentificationNumber());
        System.out.println(contractorInDatabase.get());
        if (contractorInDatabase.isPresent()) {
            invoice.getContractor().setId(contractorInDatabase.get().getId());

        }
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



