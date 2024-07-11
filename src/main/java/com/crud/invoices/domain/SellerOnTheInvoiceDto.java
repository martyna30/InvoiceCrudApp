package com.crud.invoices.domain;

import com.crud.invoices.validationGroup.Format;
import com.crud.invoices.validationGroup.LengthOfCharacters;
import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerOnTheInvoiceDto {
    private Long id;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 2, max = 60, message = "Length name of the seller must be between 2 and 30 characters")
    private String name;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Pattern(groups= Format.class, regexp = "^[0-9]{10}$" , message= "Vat identification number has a bad format")
    private String vatIdentificationNumber;
}
