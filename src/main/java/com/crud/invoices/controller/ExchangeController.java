package com.crud.invoices.controller;

import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.nbp.facade.ExchangeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*") //pozwoli na komunikację serwera warstwy frontendowej z warstwą backendową
@RestController
@RequestMapping("/v1/nbp")
public class ExchangeController {
    @Autowired
    ExchangeFacade exchangeFacade;

    @RequestMapping(method = RequestMethod.GET, value = "getTable")
    public List<ExchangeDto> getTable() {
        return exchangeFacade.getCurrencyExchangeRatesTableAndSaveToDatabase();
    }

    @RequestMapping(method = RequestMethod.GET, value = "findByDate")
    public List<Exchange> findByDate(LocalDate date) {
        return exchangeFacade.findByDate(date);
    }

}
