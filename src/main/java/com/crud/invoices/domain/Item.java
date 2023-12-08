package com.crud.invoices.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name = "ITEMS")
public class Item {
    private Long id;

    private Long number;
    private String product;
    //Invoice invoice;
    private String unit;
    private int amount;
    private BigDecimal netWorth;
    private BigDecimal amountOfVAT;

    private Integer vatRate;
    private BigDecimal grossValue;

    public Item(String product) {
        this.product = product;
    }

    public Item(Long id, Long number, String product,String unit, int amount, BigDecimal netWorth, BigDecimal amountOfVAT, Integer vatRate, BigDecimal grossValue) {
        this.id = id;
        this.number = number;
        this.product = product;
        this.amount = amount;
        this.netWorth = netWorth;
        this.amountOfVAT = amountOfVAT;
        this.vatRate = vatRate;
        this.grossValue = grossValue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column
    public Long getNumber() {
        return number;
    }

    @Column
    public String getProduct() {
        return product;
    }

    @Column
    public String getUnit() {
        return unit;
    }

    @Column
    public int getAmount() {
        return amount;
    }
    @Column
    public BigDecimal getNetWorth() {
        return netWorth;
    }
    @Column
    public BigDecimal getGrossValue() {
        return grossValue;
    }
    @Column(name="amount_of_VAT")
    public BigDecimal getAmountOfVAT() {
        return amountOfVAT;
    }

    @Column(name="VAT_rate")
    public Integer getVatRate() {
        return vatRate;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public void setAmountOfVAT(BigDecimal amountOfVAT) {
        this.amountOfVAT = amountOfVAT;
    }

    public void setVatRate(Integer vatRate) {
        this.vatRate = vatRate;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
