package com.crud.invoices.mapper;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ContractorMapper contractorMapper;



    public Invoice mapToInvoice(InvoiceDto invoiceDto) {
        return new Invoice(
                invoiceDto.getId(),
                invoiceDto.getNumber(),
                contractorMapper.mapToContractor(invoiceDto.getContractorDto()),
                invoiceDto.getDateOfInvoice(),
                invoiceDto.getDateOfSale(),
                invoiceDto.getDateOfPayment(),
                invoiceDto.getPeriodOfPayment(),
                invoiceDto.getMethodOfPayment(),
                invoiceDto.getPaid(),
                invoiceDto.getLeftToPay(),
                itemMapper.mapToItemList(invoiceDto.getItems()),
                invoiceDto.getNetAmount(),
                invoiceDto.getSumTotal(),
                invoiceDto.getAmountOfVAT());

    }

    public InvoiceDto mapToInvoiceDto(Invoice invoice) {
        return new InvoiceDto(
                invoice.getId(),
                invoice.getNumber(),
                contractorMapper.mapToContractorDto(invoice.getContractor()),
                invoice.getDateOfInvoice(),
                invoice.getDateOfSale(),
                invoice.getDateOfPayment(),
                invoice.getPeriodOfPayment(),
                invoice.getMethodOfPayment(),
                invoice.getPaid(),
                invoice.getLeftToPay(),
                itemMapper.mapToItemDtoList(invoice.getItems()),
                invoice.getNetAmount(),
                invoice.getSumTotal(),
                invoice.getAmountOfVAT());
    }


    public List<Invoice> mapToInvoiceList(final List<InvoiceDto> invoicesDtos) {
        return invoicesDtos.stream()
                .map(invoiceDto -> new Invoice(
                       invoiceDto.getId(),
                        invoiceDto.getNumber(),
                        contractorMapper.mapToContractor(invoiceDto.getContractorDto()),
                        invoiceDto.getDateOfInvoice(),
                        invoiceDto.getDateOfSale(),
                        invoiceDto.getDateOfPayment(),
                        invoiceDto.getPeriodOfPayment(),
                        invoiceDto.getMethodOfPayment(),
                        invoiceDto.getPaid(),
                        invoiceDto.getLeftToPay(),
                        itemMapper.mapToItemList(invoiceDto.getItems()),
                        invoiceDto.getNetAmount(),
                        invoiceDto.getSumTotal(),
                        invoiceDto.getAmountOfVAT()))
                        .collect(Collectors.toList());
    }

    public List<InvoiceDto> mapToInvoiceDtoList(final List<Invoice> invoices) {
        return invoices.stream()
                .map(invoice -> new InvoiceDto(
                        invoice.getId(),
                        invoice.getNumber(),
                        contractorMapper.mapToContractorDto(invoice.getContractor()),
                        invoice.getDateOfInvoice(),
                        invoice.getDateOfSale(),
                        invoice.getDateOfPayment(),
                        invoice.getPeriodOfPayment(),
                        invoice.getMethodOfPayment(),
                        invoice.getPaid(),
                        invoice.getLeftToPay(),
                        itemMapper.mapToItemDtoList(invoice.getItems()),
                        invoice.getNetAmount(),
                        invoice.getSumTotal(),
                        invoice.getAmountOfVAT()))
                .collect(Collectors.toList());
    }

    /*public List<Invoice> mapToInvoiceList(final List<InvoiceDto> invoicesDtos) {
        List<Invoice> invoices = new ArrayList<>();

        for (InvoiceDto invoiceDto : invoicesDtos) {
            Invoice invoice = new Invoice();

            invoice.setId(invoiceDto.getId());
            invoice.setNumber(invoiceDto.getNumber());
            invoice.setItems(itemMapper.mapToItemList(invoice, invoiceDto.getItems()));
            //invoice.setCustomer(invoiceDto.getCustomer());
            invoice.setDateOfInvoice(invoiceDto.getDateOfInvoice());
            invoice.setDateOfPayment(invoiceDto.getDateOfPayment());
            invoice.setIsPaid(invoiceDto.getIsPaid());
            invoice.setNetto(invoiceDto.getNetto());
            invoice.setBrutto(invoiceDto.getBrutto());
            invoices.add(invoice);
        }
        return invoices;
    }

    public List<InvoiceDto> mapToInvoiceDtoList(final List<Invoice> invoices) {
        List<InvoiceDto> invoicesDto = new ArrayList<>();

            for (Invoice invoice : invoices) {
                InvoiceDto invoiceDto = new InvoiceDto();

                invoiceDto.setId(invoice.getId());
                invoiceDto.setNumber(invoice.getNumber());
                invoiceDto.setItems(itemMapper.mapToItemDtoList(invoice.getItems()));
                invoiceDto.setDateOfInvoice(invoice.getDateOfInvoice());
                invoiceDto.setDateOfPayment(invoice.getDateOfPayment());
                invoiceDto.setIsPaid(invoice.getIsPaid());
                invoiceDto.setNetto(invoice.getNetto());
                invoiceDto.setBrutto(invoice.getBrutto());
                invoicesDto.add(invoiceDto);
            }
            return invoicesDto;
        }

    public Invoice mapToInvoice(final InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setId(invoiceDto.getId());
        invoice.setNumber(invoiceDto.getNumber());
        invoice.setItems(itemMapper.mapToItemList(invoice, invoiceDto.getItems()));
        //invoice.setCustomer(customerMapper.mapToCustomer(invoiceDto.getCustomer()));
        invoice.setDateOfInvoice(invoice.getDateOfInvoice());
        invoice.setDateOfPayment(invoice.getDateOfPayment());
        invoice.setIsPaid(invoiceDto.getIsPaid());
        invoice.setNetto(invoiceDto.getNetto());
        invoice.setBrutto(invoiceDto.getBrutto());
        return invoice;
    }

        public InvoiceDto mapToInvoiceDto(final Invoice invoice) {
       InvoiceDto invoiceDto = new InvoiceDto();
                invoiceDto.setId(invoice.getId());
                invoiceDto.setNumber(invoice.getNumber());
                invoiceDto.setItems(itemMapper.mapToItemDtoList(invoice.getItems()));
                //invoiceDto.setCustomer(customerMapper.mapToCustomerDto(invoice.getCustomer()));
                invoiceDto.setDateOfInvoice(invoice.getDateOfInvoice());
                invoiceDto.setDateOfPayment(invoice.getDateOfPayment());
                invoiceDto.setIsPaid(invoice.getIsPaid());
                invoiceDto.setNetto(invoice.getNetto());
                invoiceDto.setBrutto(invoice.getBrutto());
                return invoiceDto;
    }*/
}
