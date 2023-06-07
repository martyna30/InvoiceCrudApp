package com.crud.invoices.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListInvoicesDto {

    private long total = 0;

    private List<InvoiceDto> invoices = new ArrayList<>();

    public ListInvoicesDto(long total, List<InvoiceDto> invoices) {
        this.total = total;
        this.invoices = invoices;
    }


}
