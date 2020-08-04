package com.crud.invoices.controller;

import com.crud.invoices.domain.*;
import com.crud.invoices.mapper.SellerMapper;
import com.crud.invoices.service.SellerService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SellerController.class)
class SellerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SellerService sellerService;

    @MockBean
    SellerMapper sellerMapper;

    @Test
    public void getSeller() throws Exception {
        //Given
        List<InvoiceDto>invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto());

        List<Invoice>invoices = new ArrayList<>();
        invoices.add(new Invoice());

        Seller seller = new Seller(1L,"name",invoices);

        SellerDto sellerDto = new SellerDto(1L,"Name", invoicesDto);

        when(sellerService.getSeller(1L)).thenReturn(java.util.Optional.of(seller));
        when(sellerMapper.mapToSellerDto(seller)).thenReturn(sellerDto);

        //When & Then
        mockMvc.perform(get("/v1/seller/getSeller?sellerId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.invoices", hasSize(1)));

    }

    @Test
    public void deleteSeller() throws Exception {
        //Given
        List<InvoiceDto>invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto());

        List<Invoice>invoices = new ArrayList<>();
        invoices.add(new Invoice());

        Seller seller = new Seller(1L,"Name",invoices);

        SellerDto sellerDto = new SellerDto(1L,"Name", invoicesDto);

        //When & Then
        mockMvc.perform(delete("/v1/seller/deleteSeller?sellerId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateSeller() throws Exception {
        //Given
        List<InvoiceDto>invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto());

        List<Invoice>invoices = new ArrayList<>();
        invoices.add(new Invoice());

        SellerDto sellerDto = new SellerDto(1L,"Name", invoicesDto);

        Seller seller = new Seller(1L,"Name",invoices);

        SellerDto sellerDto2 = new SellerDto(2L,"ChangedName", invoicesDto);

        Seller seller2 = new Seller(2L,"ChangedName",invoices);

        when(sellerMapper.mapToSeller(sellerDto)).thenReturn(seller);
        when(sellerMapper.mapToSellerDto(seller2)).thenReturn(sellerDto2);

        when(sellerMapper.mapToSeller(ArgumentMatchers.any(SellerDto.class))).thenReturn(seller2);
        when(sellerService.saveSeller(seller2)).thenReturn(seller2);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(sellerDto2);//obiekt skonwertowany na JSON

        //When & Then
        mockMvc.perform(put("/v1/seller/updateSeller")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("ChangedName")))
                .andExpect(jsonPath("$.invoices", hasSize(1)));
    }

    @Test
    public void createSeller() throws Exception {
        //Given
        List<InvoiceDto>invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto());

        List<Invoice>invoices = new ArrayList<>();
        invoices.add(new Invoice());

        SellerDto sellerDto = new SellerDto(1L,"Name", invoicesDto);

        Seller seller = new Seller(1L,"Name",invoices);

        when(sellerMapper.mapToSellerDto(seller)).thenReturn(sellerDto);

        when(sellerMapper.mapToSeller(ArgumentMatchers.any(SellerDto.class))).thenReturn(seller);
        when(sellerService.saveSeller(seller)).thenReturn(seller);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(sellerDto);//obiekt skonwertowany na JSON

        //When & Then
        mockMvc.perform(post("/v1/seller/createSeller")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}