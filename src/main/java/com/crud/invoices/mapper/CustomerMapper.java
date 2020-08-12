package com.crud.invoices.mapper;

import com.crud.invoices.domain.Customer;
import com.crud.invoices.domain.CustomerDto;
import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CustomerMapper {

    @Autowired
    InvoiceMapper invoiceMapper;

    public List<Customer> mapToCustomerList(final List<CustomerDto> customerDtos) {
        List<Customer> customers = new ArrayList<>();
        for (CustomerDto customerDto : customerDtos) {
            Customer customer = new Customer();
            //customer.setId(customerDto.getId());
            customer.setInvoices(invoiceMapper.mapToInvoiceList(customerDto.getInvoices()));
            customer.setName( customerDto.getName());
            customer.setVatNumber( customerDto.getVatNumber());
            customer.setVATpayer(customerDto.isVATpayer());
            customers.add(customer);
        }
        return customers;
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

    public Customer mapToCustomer( final CustomerDto customerDto) {
        Customer customer = new Customer();

        //customer.setId(customerDto.getId());
        customer.setInvoices(invoiceMapper.mapToInvoiceList(customerDto.getInvoices()));
        customer.setName(customerDto.getName());
        customer.setVatNumber(customerDto.getVatNumber());
        customer.setVATpayer(customerDto.isVATpayer());
        return customer;
    }

    public CustomerDto mapToCustomerDto(final Customer customer) {
        return new CustomerDto(
                customer.getId(),
                invoiceMapper.mapToInvoiceDtoList(customer.getInvoices()),
                customer.getName(),
                customer.getVatNumber()
                , customer.isVATpayer());
    }
}
