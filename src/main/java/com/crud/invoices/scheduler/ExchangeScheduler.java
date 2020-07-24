package com.crud.invoices.scheduler;

import com.crud.invoices.controller.ExchangeController;
import com.crud.invoices.domain.Exchange;
import com.crud.invoices.nbp.facade.ExchangeFacade;
import com.crud.invoices.respository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ExchangeScheduler {
    private static final String SUBJECT = "Today's table NBP has been updated";
    @Autowired
    ExchangeFacade exchangeFacade;

    @Autowired
    ExchangeRepository exchangeRepository;

    @Autowired
    ExchangeController nbPcontroller;

    @Scheduled(fixedDelay = 10000)
    @Scheduled(cron = " 0 0 * * * * ")
    public void checkTable() {
        List<Exchange> currentNBPtable = exchangeRepository.findByDate(new Date());
        long size = exchangeRepository.count();

        if(size > 0) {
            System.out.println("Table is current");

        }
          else {
              exchangeFacade.getCurrencyExchangeRatesTableAndSaveToDatabase();
              System.out.println(SUBJECT);
          }
    }
}













