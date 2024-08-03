package com.crud.invoices.mapper;

import com.crud.invoices.domain.Rate;
import com.crud.invoices.domain.RateDto;
import com.crud.invoices.domain.RateFromFrontendDto;
import com.crud.invoices.domain.RateOutgoingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateMapper {

    @Autowired
    ExchangeMapper exchangeMapper;


    public Rate mapToRate(RateDto rateDto) {
        return new Rate(
                rateDto.getId(),
                rateDto.getCurrency(),
                rateDto.getRateOfExchange()
        );
    }

    public Rate mapToRate(RateFromFrontendDto rateDto) {
        return new Rate(
                rateDto.getId(),
                rateDto.getCurrency(),
                rateDto.getRateOfExchange()
        );
    }


    public RateDto mapToRateDto(Rate rate) {
        return new RateDto(
                rate.getId(),
                rate.getCurrency(),
                rate.getRateOfExchange()
        );
    }

    public RateOutgoingDto mapToRateOutgoingDto(Rate rate) {
        return new RateOutgoingDto(
            rate.getId(),
            rate.getCurrency(),
            rate.getRateOfExchange()
        );
    }


   /* public List<RateDto> mapToRateListDto(final List<Rate> rateList) {
        return rateList.stream()
                .map(rate -> new RateDto(
                        exchangeMapper.mapToExchangeDto(rate.getExchange()),
                        rate.getCurrency(),
                        rate.getRateOfExchange()))
                .collect(toList());
    }

    public List<Rate> mapToRateList(final List<RateDto> rateListDto) {
        // chyba tutaj sie zapetla
        // Exchange tworzy rates, a rates tworzy exchange przez kolejne wywolanie mapToExchange, ktory znowu tworzy rates itd
        return rateListDto.stream()
                .map(rateDto -> new Rate(
                        exchangeMapper.mapToExchange(rateDto.getExchangeDto()),
                        rateDto.getCurrency(),
                        rateDto.getRateOfExchange()))
                .collect(toList());
    }

    public Rate rate(RateDto rateDto) {
        return new Rate(
                exchangeMapper.mapToExchange(rateDto.getExchangeDto()),
                rateDto.getCurrency(),
                rateDto.getRateOfExchange()
        );
    }

    public RateDto rateDto(Rate rate) {
        return new RateDto(
                rate.getCurrency(),
                rate.getRateOfExchange()
        );*/

}
