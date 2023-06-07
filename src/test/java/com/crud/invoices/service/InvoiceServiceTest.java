package com.crud.invoices.service;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.Item;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
class InvoiceServiceTest {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ItemService itemService;

    @Test
    public void saveInvoiceWithItems() {
        //Given
        Invoice invoice = new Invoice(1L,"1");

        Item item = new Item(1L,2, new BigDecimal(22), new BigDecimal(44));
        Item item2 = new Item(1L, 2, new BigDecimal(11), new BigDecimal(22));

        item.setInvoice(invoice);
        item2.setInvoice(invoice);

        invoice.getItems().add(item);
        invoice.getItems().add(item2);

        //When
        invoiceService.saveInvoice(invoice);
        long id = invoice.getId();

        List<Item> items = itemService.getAllItems();

        //Then
        Assert.assertNotEquals(0, id);
        Assert.assertEquals(2, items.size());

        //CleanUp
        invoiceService.deleteInvoice(id);
    }

    @Test
    public void updateInvoice() {
        //Given
        Invoice invoice = new Invoice(1L,"1");
        Invoice invoice2 = new Invoice(1L,"2");

        Item item = new Item(1L,2, new BigDecimal(22), new BigDecimal(44));
        Item item2 = new Item(1L, 2, new BigDecimal(11), new BigDecimal(22));

        item.setInvoice(invoice);
        item2.setInvoice(invoice);

        invoice.getItems().add(item);
        invoice.getItems().add(item2);

        //When
        invoiceService.saveInvoice(invoice);
        String number = invoice.getNumber();
        long id = invoice.getId();

        Invoice updateInvoice = invoiceService.getInvoice(id).orElse(null);
        updateInvoice.setNumber("2");


        invoiceService.saveInvoice(updateInvoice);
        String number2 = updateInvoice.getNumber();
        long id2 = updateInvoice.getId();

        List<Invoice> invoices = invoiceService.getAllInvoices(Pageable.unpaged());

        //Then
        Assert.assertNotNull(updateInvoice);
        Assert.assertNotEquals(number, number2);
        Assert.assertEquals(1, invoices.size());

        //CleanUp
        invoiceService.deleteInvoice(id2);
    }

    @Test
    public void deleteInvoice() {
        //Given
        Invoice invoice = new Invoice(1L,"1");

        Item item = new Item(1L, 2, new BigDecimal(22), new BigDecimal(44));
        Item item2 = new Item( 1L, 2, new BigDecimal(11), new BigDecimal(22));

        item.setInvoice(invoice);
        item2.setInvoice(invoice);

        invoice.getItems().add(item);
        invoice.getItems().add(item2);

        //When
        invoiceService.saveInvoice(invoice);
        long id = invoice.getId();

        invoiceService.deleteInvoice(id);

        List<Invoice> invoices = invoiceService.getAllInvoices();

        //Then
        Assert.assertEquals(0, invoices.size());
    }
}

