package com.crud.invoices.mapper;

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
                .map(itemDto -> new Item(
                        itemDto.getId(),
                        itemDto.getNumber(),
                itemDto.getNameOfProduct(),
                itemDto.getUnit(),
                itemDto.getAmount(),
                itemDto.getNetWorth(),
                        itemDto.getVatRate(),
                itemDto.getGrossValue()))
                .collect(toList());
    }

    public List<ItemDto> mapToItemDtoList(final List<Item> items) {
        return items.stream()
                .map(item -> new ItemDto(
                        item.getId(),
                        item.getNumber(),
                        item.getNameOfProduct(),
                        item.getUnit(),
                        item.getAmount(),
                        item.getNetWorth(),
                        item.getVatRate(),
                        item.getGrossValue()))
                .collect(toList());
    }

    /*public List<ItemDto> mapToItemDtoList(final List<Item> items) {
        List<ItemDto> itemDtos = new ArrayList<>();

        for (Item item : items) {
            ItemDto itemDto = new ItemDto();
            //itemDto.setId(item.getId());
            itemDto.setQuantity(item.getQuantity());
            item.setPrice(item.getPrice());
            itemDto.setValue(item.getValue());
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }*/

    public Item mapToItem(final ItemDto itemDto) {
        return new Item(
                        itemDto.getId(),
                        itemDto.getNumber(),
                        itemDto.getNameOfProduct(),
                        itemDto.getUnit(),
                        itemDto.getAmount(),
                        itemDto.getNetWorth(),
                        itemDto.getVatRate(),
                        itemDto.getGrossValue()
        );
    }

    public ItemDto mapToItemDto(final Item item) {
        return new ItemDto(
                item.getId(),
                item.getNumber(),
                item.getNameOfProduct(),
                item.getUnit(),
                item.getAmount(),
                item.getNetWorth(),
                item.getVatRate(),
                item.getGrossValue()
        );
    }
}


