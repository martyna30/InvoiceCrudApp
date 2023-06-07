package com.crud.invoices.service;

import com.crud.invoices.domain.Contractor;
import com.crud.invoices.respository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractorService {
    @Autowired
    ContractorRepository contractorRepository;

    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }

    public Contractor saveContractor(final Contractor contractor) {
        return contractorRepository.save(contractor);
    }

    public Optional<Contractor> getContractor(final Long id) {
        return contractorRepository.findById(id);
    }

    public void deleteContractor(final Long id) {
        contractorRepository.deleteById(id);
    }



    // public <T> Optional<T> getCustomerWithSpecifiedName(String name) {
    //}
}


