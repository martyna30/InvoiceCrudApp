package com.crud.invoices.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeDto {
    @JsonProperty("table")
    private String name;
    @JsonProperty("rates")
    private List<RateDto> rates = new ArrayList<>();
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
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
