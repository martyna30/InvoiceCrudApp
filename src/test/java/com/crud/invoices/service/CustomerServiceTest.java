package com.crud.invoices.service;


import com.crud.invoices.domain.Invoice;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    InvoiceService invoiceService;

    @Test
    public void testSaveCustomerWithInvoice() {
        //Given
        Invoice invoice = new Invoice("1");

        Customer customer = new Customer("Dell");

        customer.getInvoices().add(invoice);
        invoice.setCustomer(customer);

        //When
        customerService.saveCustomer(customer);

        long customerId = customer.getId();
        long invoiceId = customer.getInvoices().get(0).getId();

        List<Invoice> invoices = invoiceService.getAllInvoices();

        //Then
        Assert.assertNotEquals(0, customerId);
        Assert.assertNotEquals(0, invoiceId);
        Assert.assertEquals(1, invoices.size());

        //CleanUp
        customerService.deleteCustomer(customerId);
    }

    @Test
    public void testUpdateCustomer() {
        //Given
        Invoice invoice = new Invoice("1");

        Customer customer = new Customer("Dell", 1, true);

        customer.getInvoices().add(invoice);
        invoice.setCustomer(customer);

        //When
        customerService.saveCustomer(customer);
        long customerId = customer.getId();
        long invoiceId = customer.getInvoices().get(0).getId();
        String name = customer.getName();
        double vatNumber = customer.getVatNumber();
        boolean isVatPayer = customer.isVATpayer();

        //When
        Customer modified = customerService.getCustomer(customerId).orElse(null);
        modified.setName("mBank");
        modified.setVatNumber(2);
        modified.setVATpayer(false);

        customerService.saveCustomer(modified);
        String name2 = modified.getName();
        double vatNumber2 = modified.getVatNumber();
        boolean isVatPayer2 = modified.isVATpayer();

        List<Customer> customers = customerService.getAllCustomers();

        //Then
        Assert.assertNotNull(modified);
        Assert.assertNotEquals(name, name2);
        Assert.assertNotEquals(vatNumber, vatNumber2);
        Assert.assertNotEquals(isVatPayer, isVatPayer2);
        Assert.assertEquals(1, customers.size());

        //CleanUp
        customerService.deleteCustomer(customerId);
    }

    @Test
    public void deleteCustomer() {
        //Given
        Invoice invoice = new Invoice("1");

        Customer customer = new Customer("Dell", 1, true);

        customer.getInvoices().add(invoice);
        invoice.setCustomer(customer);

        //Then
        customerService.saveCustomer(customer);
        long customerId = customer.getId();
        long invoiceId = customer.getInvoices().get(0).getId();

        customerService.deleteCustomer(customerId);

        List<Customer> customers = customerService.getAllCustomers();

        //Then
        Assert.assertEquals(0, customers.size());
    }

}
