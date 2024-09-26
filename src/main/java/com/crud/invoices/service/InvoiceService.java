package com.crud.invoices.service;


import com.crud.invoices.controller.ContractorNotFoundException;
import com.crud.invoices.domain.*;
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
    SellerService sellerService;

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
        this.saveContractorWithAddress(invoice);  ///sprawdz
        return this.saveInvoice(invoice);

    }

    @Transactional
    public Invoice saveInvoiceWithoutContractor(final Invoice invoice) {
       return this.saveInvoice(invoice);
    }


    @Transactional
    public Invoice saveInvoice(final Invoice invoice) {
        this.saveSeller(invoice);
        this.addDateOfPayment(invoice);
        this.calculateFirstAmount(invoice);
        this.addPrimePaymentForInvoice(invoice);
        //this.checkItems(invoice);
        Invoice invoiceWithId = invoiceRepository.save(invoice);
        this.addNumberForInvoice(invoiceWithId);
        return invoiceRepository.save(invoiceWithId);
    }



    @Transactional
    public Invoice updateInvoiceWithContractor(final Invoice invoice) throws ContractorNotFoundException {
        this.saveContractorWithAddress(invoice);
        this.saveSeller(invoice);
        this.addDateOfPayment(invoice);
        this.addNumberForInvoice(invoice);
       //items
        //this.checkItems(invoice);
        //InvoiceStatus isSettled = invoiceInDatabase.get().getIsSettled();
        this.checkPaymentsForExistingInvoice(invoice);
        //this.paymentService.addPaymentForExistingInvoice(invoice, invoice.getPaid());//zmiana
        return invoiceRepository.save(invoice);
    }





    /*private void checkItems(Invoice invoice) {
        List<Item> listItems = new ArrayList<>(invoice.getItems());

        Stream.iterate(0,i -> i + 1)
                .limit(listItems.size())
                .forEach((i)-> {

                });


    }*/



    public void calculateFirstAmount(Invoice invoice) {
        BigDecimal primePaymentOnInvoice = invoice.getPaid();
        invoice.setAmountPaid(primePaymentOnInvoice);

        this.calculateAmount(invoice);
    }

    public void calculateAmount(Invoice invoice) {
        BigDecimal amountPaid = invoice.getAmountPaid();
        BigDecimal sumTotal = invoice.getSumTotal();
        BigDecimal leftToPay = sumTotal.subtract(amountPaid);
        invoice.setLeftToPay(leftToPay);
        BigDecimal netAmount = invoice.getNetAmount();
        BigDecimal vatAmount = sumTotal.subtract(netAmount);
        invoice.setAmountOfVAT(vatAmount);
    }



    public void updateAmount(Invoice invoice, Payment firstPaymentForInvoice) {
        this.updatePaymentForExistingInvoice(invoice);
        this.calculateAmount(invoice);

    }


    @Transactional
    public void updatePaymentForExistingInvoice(Invoice invoice) {
        Optional<BigDecimal> newSumOfPayments = Optional.of(
                paymentRepository.findAllPaymentsByInvoiceId(invoice.getId())
                        .stream()
                        .map(payment1 -> payment1.getPaid())
                        .reduce(BigDecimal.valueOf(0), BigDecimal::add));
        invoice.setAmountPaid(newSumOfPayments.get());
        //BigDecimal leftToPay = invoice.getSumTotal().subtract(newSumOfPayments.get());
       // invoice.setLeftToPay(leftToPay);

        //BigDecimal sumTotal = invoice.getSumTotal();
        //BigDecimal netAmount = invoice.getNetAmount();
        //BigDecimal vatAmount = newSumOfPayments.subtract(netAmount);
        //invoice.setAmountOfVAT(vatAmount);
    }







    public void addPrimePaymentForInvoice(Invoice invoice) {
        BigDecimal paid = invoice.getPaid();
        if (paid.compareTo(BigDecimal.ZERO) > 0) {
            paymentRepository.save(
                    new Payment(
                            null,
                            invoice.getMethodOfPayment(),
                            invoice.getPaid(),
                            invoice.getDateOfInvoice(),
                            PaymentStatus.TRUE,
                            invoice));
        }
    }

    @Transactional
    public void checkPaymentsForExistingInvoice(Invoice invoice) {
         //tu popraw modyfikuj
         List<Payment> paymentsForInvoice = paymentService.findPaymentsByInvoiceId(invoice.getId());
         Optional<Payment>firstPaymentForInvoice = paymentsForInvoice.stream()
                 .filter(payment -> payment.getIsPrime().equals(PaymentStatus.TRUE))
                 .findFirst();

         if (firstPaymentForInvoice.isPresent()) {
               firstPaymentForInvoice.get().setMethodOfPayment(invoice.getMethodOfPayment());
             if (invoice.getPaid().compareTo(BigDecimal.ZERO) > 0) {
                 firstPaymentForInvoice.get().setPaid(invoice.getPaid());
             }
             if (invoice.getPaid().compareTo(BigDecimal.ZERO) <= 0) {
                paymentService.deletePayment(firstPaymentForInvoice.get().getId());
             }
             this.updateAmount(invoice, firstPaymentForInvoice.get());
           } else {
               this.addPrimePaymentForInvoice(invoice);
               this.calculateAmount(invoice);
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

    public void saveSeller(Invoice invoice) {
        Optional<Seller> selerInDatabase = sellerService.getSellerByVatIdentificationNumber(invoice.getSeller().getVatIdentificationNumber());
        selerInDatabase.ifPresent(invoice::setSeller);

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



