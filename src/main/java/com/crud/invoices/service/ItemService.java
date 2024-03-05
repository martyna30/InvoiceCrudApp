package com.crud.invoices.service;

import com.crud.invoices.domain.Item;
import com.crud.invoices.respository.ItemRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    ItemRespository itemRespository;

    public List<Item> getAllItems() {
        return itemRespository.findAll();
    }

    public Item saveItem(final Item item) {
        BigDecimal netWorth = item.getNetWorth();
        BigDecimal grossValue = item.getGrossValue();
        BigDecimal amountOfVAT = grossValue.subtract(netWorth);
        item.setAmountOfVAT(amountOfVAT);
        return itemRespository.save(item);
    }

    public Optional<Item> getItem(final Long id) {
        return itemRespository.findById(id);
    }

    public List<Item> getItemsByInvoice(final Long invoiceId) {
        return itemRespository.findItemsByInvoice(invoiceId);
    }



    public void deleteItem(final Long id) {
        itemRespository.deleteById(id);
    }
}
