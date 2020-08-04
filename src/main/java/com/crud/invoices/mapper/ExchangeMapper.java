package com.crud.invoices.mapper;

import com.crud.invoices.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ExchangeMapper {

    public List<ExchangeDto> mapToExchangeListDto(final List<Exchange> exchanges) {
        return exchanges.stream()
                .map(exchange -> new ExchangeDto(
                        exchange.getName(),
                        mapToRateListDto(exchange.getRates()),
                        exchange.getDate()))
                .collect(toList());
    }

    public List<Exchange> mapToExchangeList(final List<ExchangeDto> exchangeDtos) {
        List<Exchange> exchanges = new ArrayList<>();

        for (ExchangeDto exchangeDto : exchangeDtos) {
            Exchange exchange = new Exchange();
            exchange.setName(exchangeDto.getName());
            exchange.setDate(exchangeDto.getDate());
            exchange.setRates(this.mapToRateList(exchange, exchangeDto.getRates()));
            exchanges.add(exchange);
        }

        return exchanges;
        /*
        return exchangeDtos.stream()
                .map(exchangeDto -> new Exchange(
                        exchangeDto.getName(),
                        mapToRateList(exchangeDto.getRates()),
                        exchangeDto.getDate()) )
                .collect(toList());*/
    }

   /*public Exchange mapToExchange(ExchangeDto exchangeDto) {
        return new Exchange(
                exchangeDto.getName(),
                exchangeDto.getRates()),
                exchangeDto.getDate()
        );
    }

    public ExchangeDto mapToExchangeDto(Exchange exchange) {
        return new ExchangeDto(
                exchange.getName(),
                exchange.getRates(),
                exchange.getDate()
        );*/

   public List<RateDto> mapToRateListDto(final List<Rate> rateList) {
        return rateList.stream()
                .map(rate -> new RateDto(
                        rate.getCurrency(),
                        rate.getRateOfExchange()))
                .collect(toList());
    }

    public List<Rate> mapToRateList(Exchange exchange, final List<RateDto> rateListDto) {
        // chyba tutaj sie zapetla
        // Exchange tworzy rates, a rates tworzy exchange przez kolejne wywolanie mapToExchange, ktory znowu tworzy rates itd
        return rateListDto.stream()
                .map(rateDto -> new Rate(
                        exchange,
                        rateDto.getCurrency(),
                        rateDto.getRateOfExchange()))
                .collect(toList());
    }
}



