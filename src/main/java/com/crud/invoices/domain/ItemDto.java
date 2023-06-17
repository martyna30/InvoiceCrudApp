package com.crud.invoices.domain;

import com.crud.invoices.validationGroup.Format;
import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {
    private Long id;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    private String product;
    @NotNull(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @Min(groups= Format.class, value = 1, message="Amount of items must be at least 1")
    private int amount;
    @NotNull(groups= NotEmptyGroup.class, message = "Field can remain empty")
    private BigDecimal netWorth;
    private BigDecimal amountOfVAT;
    //@NotNull(groups= NotEmptyGroup.class, message = "Field can remain empty")
    private Integer vatRate;
    private BigDecimal grossValue;
}

