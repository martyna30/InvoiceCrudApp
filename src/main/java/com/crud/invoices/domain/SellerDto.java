package com.crud.invoices.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerDto {
    //private Long id;
    @JsonProperty
    private String name;
    //private List<InvoiceDto> invoices = new ArrayList<>();

    public SellerDto(String name) {
        this.name = name;
    }


}
