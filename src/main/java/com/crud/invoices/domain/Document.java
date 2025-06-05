package com.crud.invoices.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Document {
    private Long id;
    private String number;
    private Contractor contractor;
    private Seller seller;
    private LocalDate dateOfInvoice;
    private LocalDate dateOfSale;
    private LocalDate dateOfPayment;//usun
    private String periodOfPayment;
    private String methodOfPayment;
    private BigDecimal paid;
    private BigDecimal amountPaid;
    private BigDecimal leftToPay;
    private Rate rate;
    private List<Item> items = new ArrayList<>();
    private BigDecimal netAmount;
    private BigDecimal sumTotal;
    private BigDecimal amountOfVAT;


}
