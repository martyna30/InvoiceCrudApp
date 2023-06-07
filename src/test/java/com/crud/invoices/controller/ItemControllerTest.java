package com.crud.invoices.controller;

import com.crud.invoices.domain.Item;
import com.crud.invoices.domain.ItemDto;
import com.crud.invoices.mapper.ItemMapper;
import com.crud.invoices.service.ItemService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemMapper itemMapper;


    @Test
    public void testGetItems() throws Exception {
        List<ItemDto> itemDtoList = new ArrayList<>();

        List<Item> itemList = new ArrayList<>();

        when(itemService.getAllItems()).thenReturn(itemList);
        when(itemMapper.mapToItemDtoList(itemList)).thenReturn(itemDtoList);

        //When & Then
        mockMvc.perform(get("/v1/item/getItems").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void testGetItem() throws Exception {
        //Given
        ItemDto itemDto = new ItemDto(1L,2, new BigDecimal(22), new BigDecimal(44));

        Item item = new Item(1L,2, new BigDecimal(22), new BigDecimal(44));

        when(itemService.getItem(1L)).thenReturn(java.util.Optional.of(item));
        when(itemMapper.mapToItemDto(item)).thenReturn(itemDto);

        //When & Then
        mockMvc.perform(get("/v1/item/getItem?itemId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.quantity", is(2)))
                .andExpect(jsonPath("$.price", is(22)))
                .andExpect(jsonPath("$.value", is(44)));
    }

    @Test
    public void testDeleteItem() throws Exception {
        //Given
        ItemDto itemDto = new ItemDto( 1L,2, new BigDecimal(22), new BigDecimal(44));

        Item item = new Item(1L,2, new BigDecimal(22), new BigDecimal(44));

        //When & Then
        mockMvc.perform(delete("/v1/item/deleteItem?itemId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateItem() throws Exception {
        //Given
        ItemDto itemDto = new ItemDto(1L,2, new BigDecimal(22), new BigDecimal(44));

        Item item = new Item(1L,2, new BigDecimal(22), new BigDecimal(44));

        ItemDto itemDto2 = new ItemDto(2L, 4, new BigDecimal(2), new BigDecimal(8));

        Item item2 = new Item(2L,4, new BigDecimal(2), new BigDecimal(8));

        when(itemMapper.mapToItem(itemDto)).thenReturn(item);
        when(itemMapper.mapToItemDto(item2)).thenReturn(itemDto2);

        when(itemMapper.mapToItem(ArgumentMatchers.any(ItemDto.class))).thenReturn(item2);
        when(itemService.saveItem(item2)).thenReturn(item2);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemDto2);//obiekt skonwertowany na JSON

        //When & Then
        mockMvc.perform(put("/v1/item/updateItem")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.quantity", is(4)))
                .andExpect(jsonPath("$.price", is(2)))
                .andExpect(jsonPath("$.value", is(8)));
    }

    @Test
    public void testCreateItem() throws Exception {
        //Given
        ItemDto itemDto = new ItemDto(1L,2, new BigDecimal(22), new BigDecimal(44));

        Item item = new Item(1L,2, new BigDecimal(22), new BigDecimal(44));

        when(itemMapper.mapToItemDto(item)).thenReturn(itemDto);

        when(itemMapper.mapToItem(ArgumentMatchers.any(ItemDto.class))).thenReturn(item);
        when(itemService.saveItem(item)).thenReturn(item);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemDto);

        //When & Then
        mockMvc.perform(post("/v1/item/createItem")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
