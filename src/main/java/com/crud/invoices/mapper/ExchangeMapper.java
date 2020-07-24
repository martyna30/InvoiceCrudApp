package com.crud.invoices.mapper;

import com.crud.invoices.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ExchangeMapper {

    public List<ExchangeDto> mapToNBPListDto(final List<Exchange> nbPtables) {
        return nbPtables.stream()
                .map(nbPtable -> new ExchangeDto(nbPtable.getId(),
                        nbPtable.getName(),
                        mapToRateListDto(nbPtable.getRates()),
                        nbPtable.getDate()))
                .collect(toList());
    }

    public List<Exchange> mapToNBPList(final List<ExchangeDto> nbPtableDtos) {
        return nbPtableDtos.stream()
                .map(nbPtableDto -> new Exchange(nbPtableDto.getId(),
                        nbPtableDto.getName(),
                        mapToRateList(nbPtableDto.getRates()),
                        nbPtableDto.getData()))
                        .collect(toList());
    }

    public static Exchange mapToTable(ExchangeDto nbPtableDto) {
        return new Exchange(nbPtableDto.getId(),
                nbPtableDto.getName(),
                mapToRateList(nbPtableDto.getRates()),
                nbPtableDto.getData()
        );
    }

    public static ExchangeDto mapToTableDto(Exchange nbPtable) {
        return new ExchangeDto(nbPtable.getId(),
                nbPtable.getName(),
                mapToRateListDto(nbPtable.getRates()),
                nbPtable.getDate()
        );
    }

    public static List<RateDto> mapToRateListDto(final List<Rate> rateList) {
        return rateList.stream()
                .map(rate -> new RateDto(rate.getId(),
                        rate.getExchange(),
                        rate.getCurrency(),
                        rate.getRateOfExchange()))
                .collect(toList());
    }

    public static List<Rate> mapToRateList(final List<RateDto> rateListDto) {
        return rateListDto.stream()
                .map(rateDto -> new Rate(rateDto.getId(),
                        rateDto.getExchange(),
                        rateDto.getCurrency(),
                        rateDto.getRateOfExchange()))
                .collect(toList());
    }

    public static Rate mapToRate(RateDto rateDto) {
        return new Rate(rateDto.getId(),
                rateDto.getExchange(),
                rateDto.getCurrency(),
                rateDto.getRateOfExchange()
        );
    }

    public static RateDto mapToRateDto(Rate rate) {
        return new RateDto(rate.getId(),
                rate.getExchange(),
                rate.getCurrency(),
                rate.getRateOfExchange()
        );
    }
}
