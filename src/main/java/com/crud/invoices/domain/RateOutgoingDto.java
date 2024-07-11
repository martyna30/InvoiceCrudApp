package com.crud.invoices.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
public class RateOutgoingDto {
    private Long id;

    private String currency;

    private BigDecimal rateOfExchange;


}
