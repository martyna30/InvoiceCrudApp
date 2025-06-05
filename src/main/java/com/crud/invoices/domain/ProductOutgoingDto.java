package com.crud.invoices.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductOutgoingDto {
    private Long id;
    private String nameOfProduct;
    private String type;
    private String unit;
    private String code;
    private BigDecimal state;
    private BigDecimal netWorth;
    private Integer vatRate;
    private BigDecimal grossValue;



}
