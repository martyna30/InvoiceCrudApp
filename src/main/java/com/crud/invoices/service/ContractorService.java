package com.crud.invoices.service;

import com.crud.invoices.domain.Contractor;
import com.crud.invoices.respository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractorService {
    @Autowired
    ContractorRepository contractorRepository;

    @Autowired
    AddressService addressService;

    public List<Contractor> getAllContractors(Pageable pageable) {
        Page<Contractor> contractorPage = contractorRepository.findAll(pageable);
        return contractorPage.getContent();
    }

    public Contractor saveContractor(final Contractor contractor) {
        Optional<Contractor> contractorInDatabase = contractorRepository.findContractorByVatIdentificationNumber(contractor.getVatIdentificationNumber());
        if(contractorInDatabase.isPresent()) {
            contractor.setId(contractorInDatabase.get().getId());
            contractor.getAddress().setId(contractorInDatabase.get().getAddress().getId());
        }
        return contractorRepository.save(contractor);
    }

    public Optional<Contractor> getContractor(final Long id) {
        return contractorRepository.findById(id);
    }

    public void deleteContractor(final Long id) {
        contractorRepository.deleteById(id);
    }

    public long getCount() {
       return contractorRepository.count();

    }

    public Optional<Contractor> findContractorByVatIdentificationNumber(String vatIdentificationNumber) {
        return contractorRepository.findContractorByVatIdentificationNumber(vatIdentificationNumber);
    }
}


