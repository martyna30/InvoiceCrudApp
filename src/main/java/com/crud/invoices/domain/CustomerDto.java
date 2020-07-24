package com.crud.invoices.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
    private Long id;
    private List<InvoiceDto> invoices = new ArrayList<>();
    private String name;
    private double vatNumber;
    private boolean isVATpayer;
}
