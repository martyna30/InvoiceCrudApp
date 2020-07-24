package com.crud.invoices.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeDto {
    private Long id;
    @JsonProperty("table")
    private String name;
    @JsonProperty("rates")
    private List<RateDto> rates = new ArrayList<>();
    @JsonProperty("effectiveDate")
    private Date data;

    public ExchangeDto(String name, List<RateDto> rates ) {
        this.name = name;
        this.rates = rates;
    }
}
