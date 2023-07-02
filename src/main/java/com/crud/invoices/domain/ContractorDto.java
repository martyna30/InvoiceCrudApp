package com.crud.invoices.domain;

import com.crud.invoices.validationGroup.Format;

import com.crud.invoices.validation.VatIdentificationNumberConstraint;
import com.crud.invoices.validationGroup.LengthOfCharacters;
import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.crud.invoices.validationGroup.UniqueFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@VatIdentificationNumberConstraint(groups = UniqueFormat.class, field = "vatIdentificationNumber")
public class ContractorDto {
    private Long id;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 2, max = 30, message = "Length name of the contractor must be between 2 and 30 characters")
    private String name;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Pattern(groups= Format.class, regexp = "^[0-9]{10}$" , message= "Vat identification number has a bad format")
    private String vatIdentificationNumber;
    @Valid
    @JsonProperty("address")
    AddressDto addressDto;
}
