package com.crud.invoices.mapper;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.InvoiceDto;
import com.crud.invoices.domain.Item;
import com.crud.invoices.domain.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ItemMapper {
    @Autowired
    InvoiceMapper invoiceMapper;

    public List<Item> mapToItemList(final List<ItemDto> itemDtos) {
        return itemDtos.stream()
                .map(itemDto -> new Item(itemDto.getId(),
                        invoiceMapper.mapToInvoice(itemDto.getInvoiceDto()),
                        itemDto.getQuantity(),
                        itemDto.getPrice(),
                        itemDto.getValue()))
                        .collect(toList());
    }

    public List<ItemDto> mapToItemDtoList(final List<Item> items) {
        return items.stream()
                .map(item -> new ItemDto(item.getId(),
                        invoiceMapper.mapToInvoiceDto(item.getInvoice()),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getValue()))
                .collect(toList());

    }

    public Item mapToItem(final ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                invoiceMapper.mapToInvoice(itemDto.getInvoiceDto()),
                itemDto.getQuantity(),
                itemDto.getPrice(),
                itemDto.getValue()
        );
    }

    public ItemDto mapToItemDto(final Item item) {
        return new ItemDto(
                item.getId(),
                invoiceMapper.mapToInvoiceDto(item.getInvoice()),
                item.getQuantity(),
                item.getPrice(),
                item.getValue()
        );
    }
}


