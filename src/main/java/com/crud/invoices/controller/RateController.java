package com.crud.invoices.controller;

import com.crud.invoices.domain.RateOutgoingDto;
import com.crud.invoices.mapper.RateMapper;
import com.crud.invoices.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/nbp")
public class RateController {
    @Autowired
    RateService rateService;

    @Autowired
    RateMapper rateMapper;

    @GetMapping(value = "getRateByCurrency")
    public RateOutgoingDto getRateByCurrencyAndEffectiveDate(@RequestParam final String currency, @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate effectiveDate) throws RateNotFoundException {
       return rateMapper.mapToRateOutgoingDto(this.rateService.getRateByCurrencyAndDate(currency, effectiveDate));
    }


}
