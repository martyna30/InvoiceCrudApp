package com.crud.invoices.facade;

import com.crud.invoices.domain.ContractorDto;
import com.crud.invoices.domain.SellerDto;
import com.crud.invoices.mapper.ContractorMapper;
import com.crud.invoices.mapper.SellerMapper;
import com.crud.invoices.service.BirService;
import com.crud.invoices.service.ContractorService;
import com.crud.invoices.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BirFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(BirFacade.class);

    @Autowired
    BirService birService;
    @Autowired
    ContractorMapper contractorMapper;

    @Autowired
    SellerMapper sellerMapper;

    @Autowired
    SellerService sellerService;

    @Autowired
    ContractorService contractorService;

    public ContractorDto getContractorFromGus(String nip) throws Exception {
        Optional<ContractorDto>contractorFromGus = Optional.ofNullable(contractorMapper.mapToContractorDtoFromGus(birService.getContractorFromGus(nip)));
        //if(contractorFromGus.isPresent()) {
          // contractorService.saveContractor(contractorMapper.mapToContractor(contractorFromGus.get()));
        //}
        return contractorFromGus.orElseThrow(()-> new RuntimeException("Contractor with such VAT identification number doesn't exist"));
    }


    public SellerDto getSellerFromGus(String nip) throws Exception {
        Optional<SellerDto>sellerFromGus = Optional.ofNullable(sellerMapper.mapToSellerDtoFromGus(birService.getContractorFromGus(nip)));
        //if(sellerFromGus.isPresent()) {
           // sellerService.saveSeller(sellerMapper.mapToSeller(sellerFromGus.get()));
        //}
        return sellerFromGus.orElseThrow(() -> (new RuntimeException("Contractor with such VAT identification number doesn't exist")));
    }
}
