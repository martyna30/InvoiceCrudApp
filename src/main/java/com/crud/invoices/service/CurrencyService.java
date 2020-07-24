package com.crud.invoices.service;

import com.crud.invoices.domain.Rate;
import com.crud.invoices.respository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public List<Rate> getAllCurrency() {
        return currencyRepository.findAll();
    }

    public Rate saveCurrency(final Rate currency) {
        return currencyRepository.save(currency);
    }

    public Optional<Rate> getCurrency(final Long id) {
        return currencyRepository.findById(id);
    }

    public void deleteCurrency(final Long id) {
        currencyRepository.deleteById(id);
    }


}
