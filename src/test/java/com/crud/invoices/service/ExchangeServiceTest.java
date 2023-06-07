package com.crud.invoices.service;

import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.Rate;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class ExchangeServiceTest {

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    RateService rateService;

    @Test
    public void saveTableWithRates() {
        //Given
        List<Rate> rate = new ArrayList<>();
        rate.add(new Rate("USD", new BigDecimal(3.8997)));

        List<Exchange> exchange = new ArrayList<>();
        exchange.add(new Exchange("A", rate, LocalDate.now()));

        Exchange nbp = exchange.get(0);
        Rate currency = rate.get(0);

        nbp.getRates().add(currency);
        currency.setExchange(nbp);

        //When
        exchangeService.saveTable(nbp);
        long id = nbp.getId();
        long idRate = currency.getId();

        List<Rate> rates = rateService.getAllRates();

        //Then
        Assert.assertNotEquals(0, id);
        Assert.assertNotEquals(0, rates.size());

        //CleanUp
        exchangeService.deleteTable(id);
    }

    @Test
    public void findByDate() {
        //Given
        List<Rate> rate = new ArrayList<>();
        rate.add(new Rate("USD", new BigDecimal(3.8997)));

        List<Exchange> exchange = new ArrayList<>();
        exchange.add(new Exchange("A", rate, LocalDate.now()));

        Exchange nbp = exchange.get(0);
        Rate currency = rate.get(0);

        nbp.getRates().add(currency);
        currency.setExchange(nbp);

        //When
        exchangeService.saveTable(nbp);
        long id = nbp.getId();

        //When
        List<Exchange> nbpTable = exchangeService.findByDate(LocalDate.now());

        try {
            Assert.assertNotEquals(0, nbpTable.size());

        } finally {
            //CleanUp
            exchangeService.deleteTable(id);
        }
    }
}
