package com.crud.invoices.service;

import com.crud.invoices.domain.Customer;
import com.crud.invoices.domain.Seller;
import com.crud.invoices.respository.CustomerRepository;
import com.crud.invoices.respository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;

    public Seller saveSeller(final Seller seller) {
        return sellerRepository.save(seller);
    }

    public Optional<Seller> getSeller(final Long id) {
        return sellerRepository.findById(id);
    }

    public void deleteSeller(final Long id) {
        sellerRepository.deleteById(id);
    }
}
