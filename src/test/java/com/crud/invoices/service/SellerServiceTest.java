package com.crud.invoices.service;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.Item;
import com.crud.invoices.domain.Seller;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class SellerServiceTest {

    @Autowired
    SellerService sellerService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ItemService itemService;

    @Test
    public void saveSellerWithInvoice() {
        //Given
        Invoice invoice = new Invoice("1");

        Item item = new Item (invoice, 2, new BigDecimal(22),new BigDecimal(44));
        Item item2 = new Item (invoice, 2, new BigDecimal(11),new BigDecimal(22));

        invoice.getItems().add(item);
        invoice.getItems().add(item2);

        item.setInvoice(invoice);
        item2.setInvoice(invoice);

        Seller seller = new Seller("name");

        seller.getInvoices().add(invoice);
        invoice.setSeller(seller);

        //When
        sellerService.saveSeller(seller);
        long id = seller.getId();

        List<Invoice> invoices = invoiceService.getAllInvoices();
        List<Item> items = itemService.getAllItems();
        Seller sellerId = sellerService.getSeller(id).orElse(null);

        //Then
        Assert.assertEquals("name", sellerId.getName());

        Assert.assertEquals(1, invoices.size());
        Assert.assertEquals(2, items.size());

        //CleanUp
        sellerService.deleteSeller(id);
    }

    @Test
    public void deleteSeller() {
        //Given
        Invoice invoice = new Invoice("1");

        Item item = new Item(invoice, 2, new BigDecimal(22),new BigDecimal(44));
        Item item2 = new Item( invoice, 2, new BigDecimal(11),new BigDecimal(22));

        invoice.getItems().add(item);
        invoice.getItems().add(item2);

        item.setInvoice(invoice);
        item2.setInvoice(invoice);

        Seller seller = new Seller("name");

        seller.getInvoices().add(invoice);
        invoice.setSeller(seller);

        //When
        sellerService.saveSeller(seller);
        long idSeller = seller.getId();
        long idInvoice = invoice.getId();
        long itemId = item.getId();

        List<Invoice> invoices = invoiceService.getAllInvoices();
        List<Item> items = itemService.getAllItems();

        sellerService.deleteSeller(idSeller);
        Seller sellerId = sellerService.getSeller(idSeller).orElse(null);

        //Then
        Assert.assertEquals(null, sellerId);
        Assert.assertEquals(1, invoices.size());
        Assert.assertEquals(2, items.size());
    }
}