package com.crud.invoices.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name = "ITEMS")
public class Item {
    private Long id;
    private String product;
    //Invoice invoice;
    private int amount;
    private BigDecimal netWorth;
    private BigDecimal amountOfVAT;

    private Integer vatRate;
    private BigDecimal grossValue;

    public Item(String product) {
        this.product = product;
    }

    public Item(Long id, String product, int amount, BigDecimal netWorth, BigDecimal amountOfVAT, Integer vatRate, BigDecimal grossValue) {
        this.id = id;
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
    public String getProduct() {
        return product;
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

    public void setAmount(int amount) {
        this.amount = amount;
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
