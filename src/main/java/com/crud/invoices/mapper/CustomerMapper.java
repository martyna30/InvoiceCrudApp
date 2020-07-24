package com.crud.invoices.mapper;

import com.crud.invoices.domain.Customer;
import com.crud.invoices.domain.CustomerDto;
import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CustomerMapper {

    @Autowired
    InvoiceMapper invoiceMapper;

    public List<Customer> mapToCustomerList(final List<CustomerDto> customerDtos) {
        return customerDtos.stream()
                .map(customerDto -> new Customer(customerDto.getId(),
                        invoiceMapper.mapToInvoiceList(customerDto.getInvoices()),
                        customerDto.getName(),
                        customerDto.getVatNumber()
                        ,customerDto.isVATpayer()))
                .collect(toList());
    }

    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customers) {
        return customers.stream()
                .map(customer -> new CustomerDto(
                        customer.getId(),
                        invoiceMapper.mapToInvoiceDtoList(customer.getInvoices()),
                        customer.getName(),
                        customer.getVatNumber(),
                        customer.isVATpayer()))
                .collect(toList());
    }

    public Customer mapToCustomer(final CustomerDto customerDto) {
        return new Customer(customerDto.getId(),
                invoiceMapper.mapToInvoiceList(customerDto.getInvoices()),
                customerDto.getName(),
                customerDto.getVatNumber()
                ,customerDto.isVATpayer());
    }

    public CustomerDto mapToCustomerDto(final Customer customer) {
        return new CustomerDto(
                customer.getId(),
                invoiceMapper.mapToInvoiceDtoList(customer.getInvoices()),
                customer.getName(),
                customer.getVatNumber()
                ,customer.isVATpayer());
    }
}
