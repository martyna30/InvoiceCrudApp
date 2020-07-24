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
public class SellerDto {
    private Long id;
    private String name ="e-Invoicing Company";
    private List<Invoice> invoices = new ArrayList<>();

    public SellerDto(Long id) {
        this.id = id;
    }
}
