package com.crud.invoices.domain;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeDto {
    @JsonProperty("table")
    private String name;
    @JsonProperty("rates")
    //@JsonManagedReference
    private List<RateDto> rates = new ArrayList<>();
    @JsonProperty("effectiveDate")
    private LocalDate date;

    public ExchangeDto(String name, List<RateDto> rates) {
        this.name = name;
        this.rates = rates;
    }

    public ExchangeDto(String name, List<RateDto> rates, LocalDate date) {
        this.name = name;
        this.rates = rates;
        this.date = date;
    }
}
