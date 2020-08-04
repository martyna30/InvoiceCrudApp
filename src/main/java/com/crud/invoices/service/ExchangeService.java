package com.crud.invoices.service;

import com.crud.invoices.client.NBPclient;
import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.respository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeService {
    @Autowired
    NBPclient nbPclient;

    @Autowired
    ExchangeRepository exchangeRepository;

    public List<ExchangeDto> getTable() {
        return nbPclient.getTable();
    }

    public Exchange saveTable(final Exchange nbPtable) {
        return exchangeRepository.save(nbPtable);
    }

    public List<Exchange> findByDate(LocalDate date) {
        return exchangeRepository.retrieveTableWithSpecifiedDate(date);
    }

    public void deleteTable(Long id) {
        exchangeRepository.deleteById(id);
    }
}
