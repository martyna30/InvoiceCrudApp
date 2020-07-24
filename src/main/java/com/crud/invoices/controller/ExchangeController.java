package com.crud.invoices.controller;
import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.nbp.facade.ExchangeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public List<Exchange> findByDate(Date date) {
        return exchangeFacade.findByDate(date);
    }

    /*
    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTable", consumes = APPLICATION_JSON_VALUE )
    public void deleteTable(@RequestParam Long tableId) {
        nbPfacade.deleteTable(tableId);
    }*/

}
