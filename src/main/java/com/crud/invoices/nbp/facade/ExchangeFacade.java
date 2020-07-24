package com.crud.invoices.nbp.facade;

import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.mapper.ExchangeMapper;
import com.crud.invoices.nbp.validator.NBPvalidator;
import com.crud.invoices.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Component
public class ExchangeFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeFacade.class);

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private ExchangeMapper exchangeMapper;

    @Autowired
    NBPvalidator nbPvalidator;

    public List<ExchangeDto> getCurrencyExchangeRatesTableAndSaveToDatabase() {
        List<Exchange> tableToSave = exchangeMapper.mapToNBPList(exchangeService.getTable());
       // List<Exchange> filteredTable = nbPvalidator.validateTable(tableToSave);
        //ofNullable(filteredTable).orElseGet(nbPservice.findByDate(new Date()));
        Exchange tableToDatabase = exchangeService.saveTable(tableToSave.get(0));
        return exchangeMapper.mapToNBPListDto(asList(tableToDatabase));
    }

    public List<Exchange> findByDate(Date date) {
        return exchangeService.findByDate(date);
    }

    public void deleteTable(final Long id) {
        exchangeService.deleteTable(id);
    }
}
