package com.crud.invoices.mapper;

import com.crud.invoices.domain.Payment;
import com.crud.invoices.domain.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {
    @Autowired
    InvoiceMapper invoiceMapper;


    public Payment mapToPayment(PaymentDto paymentDto) {
        return new Payment(
                paymentDto.getId(),
                paymentDto.getMethodOfPayment(),
                paymentDto.getPaid(),
                paymentDto.getDateOfPayment()
                //invoiceMapper.mapToInvoice(paymentDto.getInvoiceComingDto())
                );
    }

    public PaymentDto mapToPaymentDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getMethodOfPayment(),
                payment.getPaid(),
                payment.getDateOfPayment()
                //invoiceMapper.mapToInvoiceOutgoingDto(payment.getInvoice())
        );
    }

    public List<Payment>mapToPayments(List<PaymentDto>paymentsDto) {
        return paymentsDto.stream()
                .map(paymentDto -> new Payment(
                        paymentDto.getId(),
                        paymentDto.getMethodOfPayment(),
                        paymentDto.getPaid(),
                        paymentDto.getDateOfPayment()
                )).collect(Collectors.toList());
    }

    public List<PaymentDto>mapToPaymentsDto(List<Payment>payments) {
        return payments.stream()
                .map(payment-> new PaymentDto(
                        payment.getId(),
                        payment.getMethodOfPayment(),
                        payment.getPaid(),
                        payment.getDateOfPayment()
                )).collect(Collectors.toList());
    }
}
