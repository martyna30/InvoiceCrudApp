package com.crud.invoices.domain;

import com.crud.invoices.validationGroup.Format;
import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDto {
    private Long id;
    private String methodOfPayment;
    @NotNull(groups = NotEmptyGroup.class, message = "Amount of paid must be at least 1")
    @Min(groups = Format.class, value = 1, message = "Amount of paid must be at least 1")
    private BigDecimal paid;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfPayment;
    //@Valid
    //@JsonProperty("invoice")
    //InvoiceComingDto invoiceComingDto;
}
