package com.crud.invoices.controller;

import com.crud.invoices.domain.InvoiceDto;
import com.crud.invoices.domain.ItemDto;
import com.crud.invoices.mapper.ItemMapper;
import com.crud.invoices.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemMapper itemMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getItems")
    public List<ItemDto> getItems() {
        return itemMapper.mapToItemDtoList(itemService.getAllItems());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getItem")
    public ItemDto getItem(@RequestParam Long itemId) throws ItemNotFoundException {
        return itemMapper.mapToItemDto(itemService.getItem(itemId).orElseThrow(ItemNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteItem", consumes = APPLICATION_JSON_VALUE )
    public void deleteItem(@RequestParam Long itemId) {
        itemService.deleteItem(itemId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateItem")
    public ItemDto updateItem(@RequestBody ItemDto itemDto) {
        return itemMapper.mapToItemDto(itemService.saveItem(itemMapper.mapToItem(itemDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createItem", consumes = APPLICATION_JSON_VALUE)
    public void createItem(@RequestBody ItemDto itemDto) {
        itemService.saveItem(itemMapper.mapToItem(itemDto));
    }
}

