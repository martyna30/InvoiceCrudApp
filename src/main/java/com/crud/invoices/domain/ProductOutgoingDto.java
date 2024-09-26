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
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductOutgoingDto {
    private Long id;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    private String nameOfProduct;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    private String type;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    private String unit;
    private String code;
    @NotNull(groups= NotEmptyGroup.class, message = "Field can remain empty")
    private BigDecimal netWorth;
    @NotNull(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @Min(groups= Format.class, value = 0, message="Vat rate must be at least 0")
    private Integer vatRate;
    private BigDecimal grossValue;



}
