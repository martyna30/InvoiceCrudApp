package com.crud.invoices.mapper;

import com.crud.invoices.domain.Rate;
import com.crud.invoices.domain.RateDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RateMapper {
    public List<RateDto> mapToRateListDto(final List<Rate> rateList) {
        return rateList.stream()
                .map(rate -> new RateDto(rate.getId(),
                        rate.getExchange(),
                        rate.getCurrency(),
                        rate.getRateOfExchange()))
                .collect(toList());
    }

    public List<Rate> mapToRateList(final List<RateDto> rateListDto) {
        return rateListDto.stream()
                .map(rateDto -> new Rate(rateDto.getId(),
                        rateDto.getExchange(),
                        rateDto.getCurrency(),
                        rateDto.getRateOfExchange()))
                .collect(toList());
    }

    public static Rate rate(RateDto rateDto) {
        return new Rate(rateDto.getId(),
                rateDto.getExchange(),
                rateDto.getCurrency(),
                rateDto.getRateOfExchange()
        );
    }

    public static RateDto rateDto(Rate rate) {
        return new RateDto(rate.getId(),
                rate.getExchange(),
                rate.getCurrency(),
                rate.getRateOfExchange()
        );
    }
}
