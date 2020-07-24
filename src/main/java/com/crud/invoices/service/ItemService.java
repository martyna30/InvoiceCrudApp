package com.crud.invoices.service;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.Item;
import com.crud.invoices.respository.ItemRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return itemRespository.save(item);
    }

    public Optional<Item> getItem(final Long id) {
        return itemRespository.findById(id);
    }

    public void deleteItem(final Long id) {
        itemRespository.deleteById(id);
    }
}
