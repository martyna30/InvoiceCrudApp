package com.crud.invoices.mapper;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class InvoiceMapper {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    CustomerMapper customerMapper;

    public List<Invoice> mapToInvoiceList(final List<InvoiceDto> invoicesDtos) {
        return invoicesDtos.stream()
                .map(invoiceDto -> new Invoice(invoiceDto.getId(),
                        invoiceDto.getNumber(),
                        itemMapper.mapToItemList(invoiceDto.getItems()),
                        customerMapper.mapToCustomerList(invoiceDto.getCustomers()),
                        invoiceDto.getNetto(),
                        invoiceDto.getBrutto(),
                        invoiceDto.getDateOfInvoice(),
                        invoiceDto.getDateOfPayment(),
                        invoiceDto.isPaid()))
                                .collect(toList());
    }

    public List<InvoiceDto> mapToInvoiceDtoList(final List<Invoice> invoices) {
        return invoices.stream()
                .map(invoice -> new InvoiceDto(invoice.getId(),
                        invoice.getNumber(),
                        itemMapper.mapToItemDtoList(invoice.getItems()),
                        customerMapper.mapToCustomerDtoList(invoice.getCustomers()),
                        invoice.getNetto(),
                        invoice.getBrutto(),
                        invoice.getDateOfInvoice(),
                        invoice.getDateOfPayment(),
                        invoice.isPaid()))
                .collect(toList());
    }

    public Invoice mapToInvoice(final InvoiceDto invoiceDto) {
        return new Invoice(
                invoiceDto.getId(),
                invoiceDto.getNumber(),
                itemMapper.mapToItemList(invoiceDto.getItems()),
                customerMapper.mapToCustomerList(invoiceDto.getCustomers()),
                invoiceDto.getNetto(),
                invoiceDto.getBrutto(),
                invoiceDto.getDateOfInvoice(),
                invoiceDto.getDateOfPayment(),
                invoiceDto.isPaid());

    }

    public InvoiceDto mapToInvoiceDto(final Invoice invoice) {
        return new InvoiceDto(
                invoice.getId(),
                invoice.getNumber(),
                itemMapper.mapToItemDtoList(invoice.getItems()),
                customerMapper.mapToCustomerDtoList(invoice.getCustomers()),
                invoice.getNetto(),
                invoice.getBrutto(),
                invoice.getDateOfInvoice(),
                invoice.getDateOfPayment(),
                invoice.isPaid());
    }
}
