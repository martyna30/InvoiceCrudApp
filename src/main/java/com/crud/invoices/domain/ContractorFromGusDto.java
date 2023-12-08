package com.crud.invoices.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractorFromGusDto {

    private Long id;

    @JsonProperty("Nazwa")
    private String name;
    @JsonProperty("Nip")
    private String vatIdentificationNumber;
    @JsonProperty("Ulica")
    private String street;
    @JsonProperty("NrNieruchomosci")
    private String streetNumber;
    @JsonProperty("KodPocztowy")
    private String postcode;
    @JsonProperty("Miejscowosc")
    private String city;
    private String country;



    //@Valid
    //@JsonProperty("address")
    //AddressDto addressDto;
}


