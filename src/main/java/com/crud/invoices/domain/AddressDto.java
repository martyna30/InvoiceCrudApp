package com.crud.invoices.domain;

import com.crud.invoices.validationGroup.Format;
import com.crud.invoices.validationGroup.LengthOfCharacters;
import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @Pattern(groups= Format.class, regexp = "^[0-9]{2}-[0-9]{3}$" , message= "Postcode has a bad format")
    private String postcode;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length of the city must be between 3 and 30 characters")
    private String city;
    private String country;
}
