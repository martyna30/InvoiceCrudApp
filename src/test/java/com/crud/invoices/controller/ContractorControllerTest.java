package com.crud.invoices.controller;

import com.crud.invoices.domain.*;
import com.crud.invoices.mapper.ContractorMapper;
import com.crud.invoices.service.ContractorService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContractorController.class)
class ContractorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractorService contractorService;

    @MockBean
    private ContractorMapper contractorMapper;

    @Test
    public void testGetCustomers() throws Exception {
        //Given
        List<ContractorDto> customerDtoList = new ArrayList<>();

        List<Contractor> customerList = new ArrayList<>();

        when(contractorService.getAllCustomers()).thenReturn(customerList);
        when(contractorService.mapToCustomerDtoList(customerList)).thenReturn(customerDtoList);

        //When & Then
        mockMvc.perform(get("/v1/customer/getCustomers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetCustomer() throws Exception {
        //Given
        List<InvoiceDto> invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto(11L, "21"));

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(11L, "21"));

        CustomerDto customerDto = new CustomerDto(1L, invoicesDto, "Dell", 1.0, true);

        Customer customer = new Customer(1L, invoices, "Dell", 1.0, true);

        when(customerService.getCustomer(1L)).thenReturn(java.util.Optional.of(customer));
        when(customerMapper.mapToCustomerDto(customer)).thenReturn(customerDto);

        //When & Then
        mockMvc.perform(get("/v1/customer/getCustomer?customerId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                //fields list
                .andExpect(jsonPath("$.invoices", hasSize(1)))
                .andExpect(jsonPath("$.invoices[0].id", is(11)))
                .andExpect(jsonPath("$.invoices[0].number", is("21")))

                .andExpect(jsonPath("$.name", is("Dell")))
                .andExpect(jsonPath("$.vatNumber", is(1.0)))
                .andExpect(jsonPath("$.vatpayer", is(true)));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        //Given
        List<InvoiceDto> invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto());

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(new Invoice());

        CustomerDto customerDto = new CustomerDto(1L, invoicesDto, "Dell", 1, true);

        Customer customer = new Customer(1L, invoiceList, "Dell", 1, true);

        //When & Then
        mockMvc.perform(delete("/v1/customer/deleteCustomer?customerId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //Given
        List<InvoiceDto> invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto());

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(new Invoice());

        CustomerDto customerDto = new CustomerDto(1L, invoicesDto, "Dell", 1, true);
        Customer customer = new Customer(1L, invoiceList, "Dell", 1, true);

        CustomerDto customerDto2 = new CustomerDto(2L, invoicesDto, "changedDell", 2, false);
        Customer customer2 = new Customer(2L, invoiceList, "changedDell", 2, false);

        when(customerMapper.mapToCustomer(customerDto)).thenReturn(customer);
        when(customerMapper.mapToCustomerDto(customer2)).thenReturn(customerDto2);

        when(customerMapper.mapToCustomer(ArgumentMatchers.any(CustomerDto.class))).thenReturn(customer2);
        when(customerService.saveCustomer(customer2)).thenReturn(customer2);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDto2);//obiekt skonwertowany na JSON
        // to nie jest ten sam obiekt, który jest przygotowany w teście dlatego any

        //When & Then
        mockMvc.perform(put("/v1/customer/updateCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("changedDell")))
                .andExpect(jsonPath("$.vatNumber", is(2.0)))
                .andExpect(jsonPath("$.vatpayer", is(false)));

    }

    @Test
    public void testCreateCustomer() throws Exception {
        //Given
        List<InvoiceDto> invoicesDto = new ArrayList<>();
        invoicesDto.add(new InvoiceDto());

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(new Invoice());

        CustomerDto customerDto = new CustomerDto(1L, invoicesDto, "Dell", 1, true);
        Customer customer = new Customer(1L, invoiceList, "Dell", 1, true);

        when(customerMapper.mapToCustomerDto(customer)).thenReturn(customerDto);

        when(customerMapper.mapToCustomer(ArgumentMatchers.any(CustomerDto.class))).thenReturn(customer);
        when(customerService.saveCustomer(customer)).thenReturn(customer);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDto);

        //When & Then
        mockMvc.perform(post("/v1/customer/createCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
