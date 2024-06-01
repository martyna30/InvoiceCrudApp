package com.crud.invoices.domain;

import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceComingDto {
    private Long id;
    //@Pattern( groups= Format.class, regexp ="^FV{1}[0-9]{1,} /20{1}[2-9]{2}$")
    private String number;
    @Valid
    @JsonProperty("contractor")
    private ContractorDto contractorDto;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfInvoice;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfSale;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfPayment;
    private String periodOfPayment;
    private String methodOfPayment;
    @NotNull(groups= NotEmptyGroup.class, message ="Amount of paid must be at least 0")
    //@Min(groups= Format.class, value = 0, message="Amount of paid must be at least 0")
    private BigDecimal paid;
    private BigDecimal amountPaid;
    private BigDecimal leftToPay;
    private InvoiceStatus isSettled;
    //@AssertFalse( groups = Settled.class, message = "Invoice was settled, you may not modify")
    //private InvoiceStatus isSettled;
    @Valid
    private List<ItemDto> items = new ArrayList<>();
    private BigDecimal netAmount;
    private BigDecimal sumTotal;
    private BigDecimal amountOfVAT;






}
