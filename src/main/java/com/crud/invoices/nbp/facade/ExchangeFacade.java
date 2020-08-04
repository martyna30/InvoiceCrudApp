package com.crud.invoices.nbp.facade;

import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.mapper.ExchangeMapper;
import com.crud.invoices.nbp.validator.NBPvalidator;
import com.crud.invoices.service.ExchangeService;
import com.crud.invoices.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@Component
public class ExchangeFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeFacade.class);

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    private ExchangeMapper exchangeMapper;

    @Autowired
    NBPvalidator nbPvalidator;

    public List<ExchangeDto> getCurrencyExchangeRatesTableAndSaveToDatabase() {
        List<Exchange> tableToSave = exchangeMapper.mapToExchangeList(exchangeService.getTable());
        Exchange tableToDatabase = exchangeService.saveTable(tableToSave.get(0));
        return exchangeMapper.mapToExchangeListDto(asList(tableToDatabase));
    }

    public List<Exchange> findByDate(LocalDate date) {
        return exchangeService.findByDate(date);
    }


}
