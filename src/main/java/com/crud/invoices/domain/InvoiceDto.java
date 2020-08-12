package com.crud.invoices.domain;

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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto {
    private Long id;
    private String number;
    private List<ItemDto> items = new ArrayList<>();
    //private CustomerDto customer;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   // @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)

    //@JsonDeserialize(using = LocalDateDeserializer.class)
    //@JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfInvoice;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    //@JsonDeserialize(using = LocalDateDeserializer.class)
    //@JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfPayment;
    InvoiceStatus isPaid;
    private double netto;
    private double brutto;

    public InvoiceDto( Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public InvoiceDto(Long id, String number, List<ItemDto> items, InvoiceStatus isPaid, double netto, double brutto) {
        this.id = id;
        this.number = number;
        this.items = items;
        this.isPaid = isPaid;
        this.netto = netto;
        this.brutto = brutto;
    }
}
