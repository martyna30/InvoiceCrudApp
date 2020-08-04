package com.crud.invoices.controller;

import com.crud.invoices.domain.CustomerDto;
import com.crud.invoices.mapper.CustomerMapper;
import com.crud.invoices.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerMapper customerMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getCustomers")
    public List<CustomerDto> getCustomers() {
        return customerMapper.mapToCustomerDtoList(customerService.getAllCustomers());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getCustomer")
    public CustomerDto getCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {
        return customerMapper.mapToCustomerDto(customerService.getCustomer(customerId).orElseThrow(CustomerNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteCustomer", consumes = APPLICATION_JSON_VALUE)
    public void deleteCustomer(@RequestParam Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateCustomer")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto) {
        return customerMapper.mapToCustomerDto(customerService.saveCustomer(customerMapper.mapToCustomer(customerDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createCustomer", consumes = APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.saveCustomer(customerMapper.mapToCustomer(customerDto));
    }
}
