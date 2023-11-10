package com.crud.invoices.controller;

import com.crud.invoices.domain.ContractorDto;
import com.crud.invoices.domain.SellerDto;
import com.crud.invoices.facade.BirFacade;
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

    @Autowired
    BirFacade birFacade;


    @GetMapping( value = "getContractorFromGus")
    public ResponseEntity<ContractorDto> getContractorFromGus(@RequestParam String nip) {
        try {
            return ResponseEntity.ok(birFacade.getContractorFromGus(nip));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "getSellerFromGus")
    public ResponseEntity<SellerDto> getSellerByVatIdentificationNumber(@RequestParam String nip) {
        try {
            return ResponseEntity.ok(birFacade.getSellerFromGus(nip));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
