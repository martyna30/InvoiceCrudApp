package com.crud.invoices.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {
    private Long id;
    private String product;
    private int amount;
    private BigDecimal netWorth;
    private BigDecimal amountOfVAT;
    private Integer vatRate;
    private BigDecimal grossValue;
}

