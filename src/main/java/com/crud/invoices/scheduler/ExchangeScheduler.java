package com.crud.invoices.scheduler;

import com.crud.invoices.domain.Exchange;
import com.crud.invoices.nbp.facade.ExchangeFacade;
import com.crud.invoices.respository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ExchangeScheduler {
    private static final String SUBJECT = "Today's table NBP has been updated";
    @Autowired
    ExchangeFacade exchangeFacade;

    @Autowired
    ExchangeRepository exchangeRepository;

    //@Scheduled(fixedDelay =3600000)
    @Scheduled(cron = " 0 0 * * * * ")
    public void checkTable() {
        List<Exchange> currentNBPtable = exchangeRepository.retrieveTableWithSpecifiedDate(LocalDate.now());

        long size = currentNBPtable.size();

        if (size > 0) {
            System.out.println("Table is current");

        } else {
            exchangeFacade.getCurrencyExchangeRatesTableAndSaveToDatabase();
            System.out.println(SUBJECT);
        }
    }
}













