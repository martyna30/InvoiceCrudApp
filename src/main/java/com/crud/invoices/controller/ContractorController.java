package com.crud.invoices.controller;

import com.crud.invoices.domain.ContractorDto;
import com.crud.invoices.domain.ListContractorsDto;
import com.crud.invoices.mapper.ContractorMapper;
import com.crud.invoices.service.ContractorService;
import com.crud.invoices.validationGroup.OrderChecks;
import com.crud.invoices.validationGroup.OrderChecks2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/contractor")
public class ContractorController {
    @Autowired
    ContractorService contractorService;

    @Autowired
    ContractorMapper contractorMapper;

    @GetMapping(value = "getContractors")
    public ResponseEntity<ListContractorsDto> getContractors(@RequestParam int page, @RequestParam int size) throws ResponseStatusException  {
        PageRequest pageRequest = PageRequest.of(page, size);
        try {
            return ResponseEntity.ok(new ListContractorsDto(contractorService.getCount(),
                    contractorMapper.mapToContractorDtoList(contractorService.getAllContractors(pageRequest))));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping( value = "getContractor")
    public ResponseEntity<ContractorDto> getContractor(@RequestParam Long contractorId) {
        try {
            return ResponseEntity.ok(contractorMapper.mapToContractorDto(contractorService.getContractor(contractorId).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "getContractorWithSpecifiedName")
    public List<ContractorDto> getContractorWithSpecifiedName(@RequestParam String name) {
            return contractorMapper.mapToContractorDtoList(contractorService.getContractorWithSpecifiedName(name));
    }

    @GetMapping(value = "getContractorByName")
    public ResponseEntity<ContractorDto> getContractorByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(contractorMapper.mapToContractorDto(contractorService.getContractorByName(name).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "deleteContractor", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  deleteContractor(@RequestParam Long contractorId) {
        try {
            contractorService.deleteContractor(contractorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "updateContractor")
    public ResponseEntity<Object> updateContractor(@Validated(value = {OrderChecks2.class}) @Valid @RequestBody ContractorDto contractorDto, Errors errors) {
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
        contractorService.saveContractor(contractorMapper.mapToContractor(contractorDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "createContractor", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createContractor(@Validated(value = {OrderChecks.class})
                                                       @Valid @RequestBody
                                                       ContractorDto contractorDto,
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
        contractorService.saveContractor(contractorMapper.mapToContractor(contractorDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
