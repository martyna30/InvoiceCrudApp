package com.crud.invoices.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateDto {
    private Long id;
    //@JsonBackReference
    //ExchangeDto exchangeDto;
    @JsonProperty("code")
    private String currency;
    @JsonProperty("mid")
    private BigDecimal rateOfExchange;

    public RateDto(Long id, String currency, BigDecimal rateOfExchange) {
        this.id = id;
        this.currency = currency;
        this.rateOfExchange = rateOfExchange;
    }
}

