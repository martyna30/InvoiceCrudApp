package com.crud.invoices.service;

import com.crud.invoices.client.NBPclient;
import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.respository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Exchange> findTable(final Long id) {
        return exchangeRepository.findById(id);
    }

    public void deleteTable(final Long id) {
        exchangeRepository.deleteById(id);
    }

    public List<Exchange> findByDate(Date date) {
        return exchangeRepository.findByDate(date);
    }
}
