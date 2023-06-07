package com.crud.invoices.controller;

import com.crud.invoices.domain.*;
import com.crud.invoices.mapper.InvoiceMapper;
import com.crud.invoices.service.InvoiceService;
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

import java.util.ArrayList;
import java.util.List;

import static com.crud.invoices.domain.InvoiceStatus.TRUE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private InvoiceMapper invoiceMapper;

    @Test
    public void testGetInvoices() throws Exception {
        //Given
        List<InvoiceDto> invoiceDtoList = new ArrayList<>();

        List<Invoice> invoiceList = new ArrayList<>();

        when(invoiceService.getAllInvoices()).thenReturn(invoiceList);
        when(invoiceMapper.mapToInvoiceDtoList(invoiceList)).thenReturn(invoiceDtoList);

        //When & Then
        mockMvc.perform(get("/v1/invoice/getInvoices").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetInvoice() throws Exception {
        //Given
        List<ItemDto> itemDto = new ArrayList<>();
        itemDto.add(new ItemDto());

        InvoiceDto invoiceDto = new InvoiceDto(1L,
                "1",
                itemDto,
               TRUE,
                20.0,
                24.0
                );

        Invoice invoice = new Invoice();

        when(invoiceService.getInvoice(1L)).thenReturn(java.util.Optional.of(invoice));
        when(invoiceMapper.mapToInvoiceDto(invoice)).thenReturn(invoiceDto);

        //When & Then
        mockMvc.perform(get("/v1/invoice/getInvoice?invoiceId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.number", is("1")))
                //fields list items
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.isPaid", is("TRUE")))
                .andExpect(jsonPath("$.netto", is(20.0)))
                .andExpect(jsonPath("$.brutto", is(24.0)));


    }

    @Test
    public void testDeleteInvoice() throws Exception {
        //Given
        List<ItemDto> itemDto = new ArrayList<>();
        itemDto.add(new ItemDto());

        List<CustomerDto> customersDto = new ArrayList<>();
        customersDto.add(new CustomerDto());

        InvoiceDto invoiceDto = new InvoiceDto(1L,
                "1",
                itemDto,
                TRUE,
                20.0,
                24.0);

        Invoice invoice = new Invoice();

        //When & Then
        mockMvc.perform(delete("/v1/invoice/deleteInvoice?invoiceId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateInvoice() throws Exception {
        //Given
        List<ItemDto> itemDto = new ArrayList<>();
        itemDto.add(new ItemDto());

        List<Item> items = new ArrayList<>();
        items.add(new Item());

        InvoiceDto invoiceDto = new InvoiceDto(1L, "1", itemDto, TRUE, 20.0, 24.0);

        Invoice invoice = new Invoice( 1L, "1", items,  TRUE, 20.0, 24.0);

        InvoiceDto invoiceDto2 = new InvoiceDto(2L, "2", itemDto,  TRUE, 30.0, 37.0 );

        Invoice invoice2 = new Invoice(2L,"2", items, TRUE, 30.0, 37.0 );

        when(invoiceMapper.mapToInvoice(invoiceDto)).thenReturn(invoice);
        when(invoiceMapper.mapToInvoiceDto(invoice2)).thenReturn(invoiceDto2);
        when(invoiceMapper.mapToInvoice(ArgumentMatchers.any(InvoiceDto.class))).thenReturn(invoice2);
        when(invoiceService.saveInvoice(invoice2)).thenReturn(invoice2);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(invoiceDto2);
        System.out.println(jsonContent);

        //When & Then
        mockMvc.perform(put("/v1/invoice/updateInvoice")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.number", is("2")))
                //fields list items
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.isPaid", is("TRUE")))
                .andExpect(jsonPath("$.netto", is(30.0)))
                .andExpect(jsonPath("$.brutto", is(37.0)));

    }

    @Test
    public void testCreateInvoice() throws Exception {
        //Given
        List<ItemDto> itemDto = new ArrayList<>();
        itemDto.add(new ItemDto());

        List<Item> items = new ArrayList<>();
        items.add(new Item());

        InvoiceDto invoiceDto = new InvoiceDto(1L, "1", itemDto, TRUE, 20.0, 24.0);

        Invoice invoice = new Invoice( 1L, "1", items,  TRUE, 20.0, 24.0);

        when(invoiceMapper.mapToInvoiceDto(invoice)).thenReturn(invoiceDto);

        when(invoiceMapper.mapToInvoice(ArgumentMatchers.any(InvoiceDto.class))).thenReturn(invoice);
        when(invoiceService.saveInvoice(invoice)).thenReturn(invoice);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(invoiceDto);

        //When & Then
        mockMvc.perform(post("/v1/invoice/createInvoice")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());

    }
}
