package com.crud.invoices.service;

import com.crud.invoices.domain.Address;
import com.crud.invoices.respository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;


    public Address saveAddress (final Address address) {
        return addressRepository.save(address);
    }

    public Optional<Address> getAddress(final Long id) {
        return addressRepository.findById(id);
    }

    public void deleteAddress(final Long id) {
         addressRepository.deleteById(id);
    }
}
