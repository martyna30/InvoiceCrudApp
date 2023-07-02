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
public class ListContractorsDto {

    private long total = 0;

    private List<ContractorDto> contractors = new ArrayList<>();

    public ListContractorsDto(long total, List<ContractorDto> contractors) {
        this.total = total;
        this.contractors = contractors;
    }





}
