package com.crud.invoices.controller;

import com.crud.invoices.domain.SellerDto;
import com.crud.invoices.mapper.SellerMapper;
import com.crud.invoices.service.SellerService;
import com.crud.invoices.validationGroup.OrderChecks;
import com.crud.invoices.validationGroup.OrderChecks2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;
    @Autowired
    SellerMapper sellerMapper;



    @GetMapping( value = "getSeller")
    public ResponseEntity<SellerDto> getSeller(@RequestParam Long sellerId) {
        try {
            return ResponseEntity.ok(sellerMapper.mapToSellerDto(sellerService.getSeller(sellerId).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "getSellerByName")
    public ResponseEntity<SellerDto> getSellerByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(sellerMapper.mapToSellerDto(sellerService.getSellerByName(name).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "getSellerByAppUser")
    public ResponseEntity <SellerDto> getSellerByAppUser(@RequestParam String username) {
        try {
            return ResponseEntity.ok(sellerMapper.mapToSellerDto(sellerService.getSellerByAppUser(username)));
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "getSellerByVatIdentificationNumber")
    public ResponseEntity<SellerDto> getSellerByVatIdentificationNumber(@RequestParam String vatIdentificationNumber) {
        try {
            return ResponseEntity.ok(sellerMapper.mapToSellerDto(sellerService.getSellerByVatIdentificationNumber(vatIdentificationNumber).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "deleteSeller", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  deleteSeller(@RequestParam Long sellerId) {
        try {
            sellerService.deleteSeller(sellerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "updateSeller")
    public ResponseEntity<Object> updateSeller(@Validated(value = {OrderChecks2.class}) @Valid @RequestBody SellerDto sellerDto, Errors errors) {
        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if(!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        sellerMapper.mapToSellerDto(sellerService.saveSeller(sellerMapper.mapToSeller(sellerDto)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "createSeller")
    public ResponseEntity<Object> createSeller(@Validated(value = {OrderChecks.class})
                                                   @Valid @RequestBody SellerDto sellerDto,
                                               Errors errors) {
        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if(!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        sellerService.saveSeller(sellerMapper.mapToSeller(sellerDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
