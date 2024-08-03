package com.crud.invoices.service;

import com.crud.invoices.controller.RateNotFoundException;
import com.crud.invoices.domain.Rate;
import com.crud.invoices.respository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RateService {
    @Autowired
    ExchangeService exchangeService;

    @Autowired
    RateRepository rateRepository;

    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    public Rate saveCurrency(final Rate rate) {
        return rateRepository.save(rate);
    }

    public Optional<Rate> getCurrency(final Long id) {
        return rateRepository.findById(id);
    }



    public Rate getRateByCurrencyAndDate(final String currency, final LocalDate effectiveDate) throws RateNotFoundException {
        Optional<Rate> rate = rateRepository.findByCurrencyAndEffectiveDate(currency, effectiveDate);

        if (!rate.isPresent()) {
            LocalDate effectiveDateFromYesterday = effectiveDate.minusDays(1);
            rate = rateRepository.findByCurrencyAndEffectiveDate(currency, effectiveDateFromYesterday);
            if(!rate.isPresent()) {
                rate = rateRepository.findLastRateBeforeDate(currency, effectiveDateFromYesterday);
            }
        }
        return rate.orElseThrow(RateNotFoundException::new);
    }
}


