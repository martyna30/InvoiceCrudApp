package com.crud.invoices.service;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceStatus;
import com.crud.invoices.domain.Payment;
import com.crud.invoices.exception.InvoiceNullPointerException;
import com.crud.invoices.respository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    InvoiceService invoiceService;

    public Payment savePayment(Payment payment, final Long invoiceId) {
        Optional<Invoice> invoiceInDatabase = invoiceService.getInvoice(invoiceId);
        InvoiceStatus isSettled = invoiceInDatabase.get().getIsSettled();
        if (isSettled.equals(InvoiceStatus.FALSE)) {
            invoiceInDatabase.get().setIsSettled(InvoiceStatus.TRUE);
        }
        BigDecimal currentPayment = payment.getPaid();
        payment = new Payment(
                null,
                payment.getMethodOfPayment(),
                currentPayment,
                payment.getDateOfPayment(),
                invoiceInDatabase.get()
        );
        this.addPaymentForExistingSettledInvoice(invoiceInDatabase.get(),currentPayment);
        return  paymentRepository.save(payment);
    }



    @Transactional
    public void addPaymentForExistingSettledInvoice(Invoice invoice, BigDecimal currentPayment) {
        Optional<BigDecimal> sumOfPayments = Optional.of(
                paymentRepository.findAllPaymentsByInvoiceId(invoice.getId())
                        .stream()
                        .map(payment1 -> payment1.getPaid())
                        .reduce(BigDecimal.valueOf(0), BigDecimal::add));
        sumOfPayments = Optional.of(sumOfPayments.get().add(currentPayment));
        invoice.setAmountPaid(sumOfPayments.get());
        BigDecimal leftToPay = invoice.getSumTotal().subtract(sumOfPayments.get());
        invoice.setLeftToPay(leftToPay);
    }


    public Optional<Payment> getPayment(final Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> findPaymentsByInvoiceId(final Long invoiceId) {
        return paymentRepository.findAllPaymentsByInvoiceId(invoiceId);
    }

    public Optional<Payment> findFirstPaymentByInvoiceId(final Long invoiceId) {
        return Optional.ofNullable(paymentRepository.findFirstByInvoiceId(invoiceId).orElseThrow(InvoiceNullPointerException::new));
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments;
    }

    public void deletePayment(Long paymentId) {
        this.paymentRepository.deleteById(paymentId);
    }
}
