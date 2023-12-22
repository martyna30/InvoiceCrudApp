package com.crud.invoices.service;

import com.crud.invoices.domain.Rate;
import com.crud.invoices.respository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateService {



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


}


