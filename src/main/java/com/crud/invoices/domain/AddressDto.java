package com.crud.invoices.domain;

import com.crud.invoices.validationGroup.LengthOfCharacters;
import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
    private Long id;
    private String street;
    private String streetNumber;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    private String postcode;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length of the city must be between 3 and 30 characters")
    private String city;
}
