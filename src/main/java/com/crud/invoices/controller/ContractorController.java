package com.crud.invoices.controller;

import com.crud.invoices.domain.ContractorDto;
import com.crud.invoices.mapper.ContractorMapper;
import com.crud.invoices.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/customer")
public class ContractorController {
    @Autowired
    ContractorService contractorService;

    @Autowired
    ContractorMapper contractorMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getContractors")
    public List<ContractorDto> getContractors() {
        return contractorMapper.mapToContractorDtoList(contractorService.getAllContractors());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getContractor")
    public ContractorDto getContractor(@RequestParam Long contractorId) throws ContractorNotFoundException {
        return contractorMapper.mapToContractorDto(contractorService.getContractor(contractorId).orElseThrow(ContractorNotFoundException::new));
    }

    @GetMapping(value = "getContractorWithSpecifiedName")
    public ContractorDto getContractorWithSpecifiedName(@RequestParam String name) throws ContractorNotFoundException {
        // return customerMapper.mapToContractorDto(customerService.getWContractorWithSpecifiedName(name).orElseThrow(ContractorNotFoundException::new));
        // }
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteContractor", consumes = APPLICATION_JSON_VALUE)
    public void deleteContractor(@RequestParam Long contractorId) {
        contractorService.deleteContractor(contractorId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateContractor")
    public ContractorDto updateContractor(@RequestBody ContractorDto contractorDto) {
        return contractorMapper.mapToContractorDto(contractorService.saveContractor(contractorMapper.mapToContractor(contractorDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createContractor", consumes = APPLICATION_JSON_VALUE)
    public ContractorDto createContractor(@RequestBody ContractorDto contractorDto) {
        return  contractorMapper.mapToContractorDto(contractorService.saveContractor(contractorMapper.mapToContractor(contractorDto)));
    }
}
