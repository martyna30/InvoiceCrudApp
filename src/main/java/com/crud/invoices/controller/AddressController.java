package com.crud.invoices.controller;

import com.crud.invoices.domain.AddressDto;
import com.crud.invoices.mapper.AddressMapper;
import com.crud.invoices.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    AddressMapper addressMapper;


    @PostMapping(value = "createAddress", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAddress(@RequestBody AddressDto addressDto) {
       addressService.saveAddress(addressMapper.mapToAddress(addressDto));
       return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
