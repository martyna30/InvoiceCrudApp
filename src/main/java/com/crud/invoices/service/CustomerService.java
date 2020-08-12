package com.crud.invoices.service;

import com.crud.invoices.client.ViesClient;
import com.crud.invoices.domain.Customer;
import com.crud.invoices.domain.Item;
import com.crud.invoices.respository.CustomerRepository;
import com.crud.invoices.respository.ItemRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.soap.SOAPMessage;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(final Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomer(final Long id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomer(final Long id) {
        customerRepository.deleteById(id);
    }
}


