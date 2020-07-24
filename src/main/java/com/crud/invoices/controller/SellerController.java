package com.crud.invoices.controller;

import com.crud.invoices.domain.CustomerDto;
import com.crud.invoices.domain.SellerDto;
import com.crud.invoices.mapper.CustomerMapper;
import com.crud.invoices.mapper.SellerMapper;
import com.crud.invoices.service.CustomerService;
import com.crud.invoices.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/seller")
public class SellerController {
    @Autowired
    SellerService sellerService;

    @Autowired
    SellerMapper sellerMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getSeller")
    public SellerDto getSeller(@RequestParam Long sellerId) throws SellerNotFoundException {
        return sellerMapper.mapToSellerDto(sellerService.getSeller(sellerId).orElseThrow(SellerNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteSeller", consumes = APPLICATION_JSON_VALUE)
    public void deleteSeller(@RequestParam Long sellerId) {
        sellerService.deleteSeller(sellerId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateSeller")
    public SellerDto updateSeller(@RequestBody SellerDto sellerDto) {
        return sellerMapper.mapToSellerDto(sellerService.saveSeller(sellerMapper.mapToSeller(sellerDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createSeller", consumes = APPLICATION_JSON_VALUE)
    public void createSeller(@RequestBody SellerDto sellerDto) {
        sellerService.saveSeller(sellerMapper.mapToSeller(sellerDto));
    }
}
