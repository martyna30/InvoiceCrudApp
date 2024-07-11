package com.crud.invoices.service;

import com.crud.invoices.client.NBPclient;
import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.respository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExchangeService {
    @Autowired
    NBPclient nbPclient;

    @Autowired
    ExchangeRepository exchangeRepository;

    public List<ExchangeDto> getTable() {
        return nbPclient.getTable();
    }

    @Transactional
    public Exchange saveTable(final Exchange nbPtable) {
        List<Exchange> exchangeTables = this.findByDate(nbPtable.getDate());
        if (exchangeTables.size() > 0) {
            Exchange exchangeTablesIn = exchangeTables.stream().findFirst().get();
            exchangeTablesIn.setName(nbPtable.getName());
            exchangeTablesIn.setRates(nbPtable.getRates());

            return exchangeRepository.save(exchangeTablesIn);
        } else {
            return exchangeRepository.save(nbPtable);
        }
    }

    public List<Exchange> findByDate(LocalDate date) {
        return exchangeRepository.retrieveTableWithSpecifiedDate(date);
    }

    public void deleteTable(Long id) {
        exchangeRepository.deleteById(id);
    }
}
