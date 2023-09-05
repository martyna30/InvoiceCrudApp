package com.crud.invoices.controller;

import com.crud.invoices.domain.ContractorFromGusDto;
import com.crud.invoices.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/gus")
public class BirController {

    @Autowired
    ContractorService contractorService;


    @GetMapping( value = "getContractorFromGus")
    public ResponseEntity<ContractorFromGusDto> getContractorFromGus(@RequestParam String nip) {
        try {
            return ResponseEntity.ok(contractorService.getContractorFromGus(nip));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
