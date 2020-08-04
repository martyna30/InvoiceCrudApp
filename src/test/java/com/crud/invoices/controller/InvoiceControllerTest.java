package com.crud.invoices.controller;

import com.crud.invoices.domain.*;
import com.crud.invoices.mapper.CustomerMapper;
import com.crud.invoices.mapper.InvoiceMapper;
import com.crud.invoices.service.CustomerService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
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

        List<CustomerDto> customersDto = new ArrayList<>();
        customersDto.add(new CustomerDto());

        SellerDto sellerDto = new SellerDto(1L,"name");

        InvoiceDto invoiceDto = new InvoiceDto(
                1L,
                "1",
                itemDto,
                customersDto,
                sellerDto,
                20.0,
                24.0,
                LocalDate.of(2020,07,20),
                LocalDate.of(2020,07,30),
                true);

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
                //fields list customers
                .andExpect(jsonPath("$.customers", hasSize(1)))
                //fields SellerDto
                .andExpect(jsonPath("$.seller.id", is(1)))

                .andExpect(jsonPath("$.netto",is(20.0)))

                .andExpect(jsonPath("$.brutto", is(24.0)))
                .andExpect(jsonPath("$.dateOfInvoice", is("2020-07-20")))
                .andExpect(jsonPath("$.dateOfPayment", is("2020-07-30")))
                .andExpect(jsonPath("$.paid", is(true)));

    }

    @Test
    public void testDeleteInvoice() throws Exception {
        //Given
        List<ItemDto> itemDto = new ArrayList<>();
        itemDto.add(new ItemDto());

        List<CustomerDto> customersDto = new ArrayList<>();
        customersDto.add(new CustomerDto());

        SellerDto sellerDto = new SellerDto(1L, "name");
        InvoiceDto invoiceDto = new InvoiceDto(
                1L,
                "1",
                itemDto,
                customersDto,
                sellerDto,
                20.0,
                24.0,
               LocalDate.of(2020,07,20),
               LocalDate.of(2020,07,30),
                true);

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

        List<CustomerDto> customersDto = new ArrayList<>();
        customersDto.add(new CustomerDto());

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        SellerDto sellerDto = new SellerDto(1L, "name");
        Seller seller = new Seller(1L,"name");

        InvoiceDto invoiceDto = new InvoiceDto(1L, "1", itemDto, customersDto, sellerDto, 20.0, 24.0,
                LocalDate.of(2020,07,20), LocalDate.of(2020,07,30),true);

        Invoice invoice = new Invoice(1L, "1", items, customers, seller, 20.0, 24.0,
                LocalDate.of(2020,07,20), LocalDate.of(2020,07,30), true);

        InvoiceDto invoiceDto2 = new InvoiceDto(2L, "2", itemDto, customersDto, sellerDto, 30.0, 37.0,
                LocalDate.of(2020,07,21), LocalDate.of(2020,07,31),
                true);

        Invoice invoice2 = new Invoice(2L, "2", items, customers, seller, 30.0, 37.0,
                LocalDate.of(2020,07,21), LocalDate.of(2020,07,31), true);
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
                //fields list customers
                .andExpect(jsonPath("$.customers", hasSize(1)))
                //fields SellerDto
                .andExpect(jsonPath("$.seller.id", is(1)))
                .andExpect(jsonPath("$.netto",is(30.0)))
                .andExpect(jsonPath("$.brutto",is(37.0)))
                .andExpect(jsonPath("$.dateOfInvoice.year", is("2020")))
                .andExpect(jsonPath("$.dateOfInvoice.month", is("07")))
                .andExpect(jsonPath("$.dateOfInvoice.dayOfMonth", is("21")))
                .andExpect(jsonPath("$.dateOfPayment.year", is("2020")))
                .andExpect(jsonPath("$.dateOfInvoice.month", is("07")))
                .andExpect(jsonPath("$.dateOfInvoice.dayOfMonth", is("31")))
                .andExpect(jsonPath("$.paid", is(true)));
    }

    @Test
    public void testCreateInvoice() {
    }
}