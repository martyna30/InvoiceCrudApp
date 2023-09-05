package com.crud.invoices.service;

import com.crud.invoices.client.bir.BirClient;
import com.crud.invoices.client.bir.RegonType;
import com.crud.invoices.client.bir.ReportClient;
import com.crud.invoices.client.bir.ReportParser;
import com.crud.invoices.domain.Contractor;
import com.crud.invoices.domain.ContractorFromGusDto;
import com.crud.invoices.mapper.ContractorMapper;
import com.crud.invoices.respository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tempuri.IUslugaBIRzewnPubl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContractorService {

    @Autowired
    ContractorRepository contractorRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    BirClient birClient;
    @Autowired
    ContractorMapper contractorMapper;

    public ContractorFromGusDto getContractorFromGus(final String nip) throws Exception {
        IUslugaBIRzewnPubl port = birClient.prepareApi();
        RegonType regonType = new RegonType();
        regonType.setNip("5741739416");
        ReportClient reportClient = new ReportClient(birClient, regonType);
        String basic = reportClient.getReport(regonType);
        ReportParser reportParser = new ReportParser();
        Map<String, String> map = reportParser.parseReport(basic);
        ContractorFromGusDto contractorFromGusDto = contractorMapper.mapToContractorFromGusDto(map);
        return contractorFromGusDto;
    }

        /*for(Map.Entry<String, String> maps : map.entrySet()) {
            if (maps.getKey().contains("Nazwa")) {
                contractorFromGusDto.setName(maps.getValue());
                System.out.println(contractorFromGusDto.getName());
            }
            if (maps.getKey().contains("Nip")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }
            if (maps.getKey().contains("Ulica")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("NrNieruchomosci")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("KodPocztowy")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("Miejscowosc")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }
        }

        }*/


    public List<Contractor> getAllContractors(Pageable pageable) {
        Page<Contractor> contractorPage = contractorRepository.findAll(pageable);
        return contractorPage.getContent();
    }

    public Contractor saveContractor(final Contractor contractor) {
        Optional<Contractor> contractorInDatabase = contractorRepository.findContractorByVatIdentificationNumber(contractor.getVatIdentificationNumber());
        if (contractorInDatabase.isPresent()) {
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

    public List<Contractor> getContractorWithSpecifiedName(String name) {
        List<Contractor>contractors = contractorRepository.findByNameContainsIgnoreCase(name);
        if(contractors.size() > 0) {
            return contractors;
        }
        return contractors;
    }

    public Optional<Contractor> getContractorByName(String name) {
        Optional<Contractor> contractorWithName = contractorRepository.findContractorByNameIgnoreCase(name);
        return Optional.ofNullable(contractorWithName
                .orElseThrow(() -> new RuntimeException("Contractor doesn't exist")));
    }


}


