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
    private String nameOfProduct;
    //Invoice invoice;
    private String unit;
    private int amount;
    private BigDecimal netWorth;
    private BigDecimal amountOfVAT;

    private Integer vatRate;
    private BigDecimal grossValue;
    Product product;

    public Item(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public Item(Long id, Long number, String nameOfProduct,String unit, int amount,
                BigDecimal netWorth, BigDecimal amountOfVAT, Integer vatRate, BigDecimal grossValue) {
        this.id = id;
        this.number = number;
        this.nameOfProduct = nameOfProduct;
        this.unit = unit;
        this.amount = amount;
        this.netWorth = netWorth;
        this.amountOfVAT = amountOfVAT;
        this.vatRate = vatRate;
        this.grossValue = grossValue;
    }

    public Item(Long id, Long number, String nameOfProduct, String unit, int amount,
                BigDecimal netWorth, Integer vatRate, BigDecimal grossValue) {
        this.id = id;
        this.number = number;
        this.nameOfProduct = nameOfProduct;
        this.unit = unit;
        this.amount = amount;
        this.netWorth = netWorth;
        this.vatRate = vatRate;
        this.grossValue = grossValue;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID", unique = true)
    public Long getId() {
        return id;
    }


    @JoinColumn(name ="PRODUCT_ID")
    @ManyToOne(cascade = {CascadeType.MERGE})
    public Product getProduct() {
        return product;
    }


    @Column
    public Long getNumber() {
        return number;
    }
    @Column
    public String getNameOfProduct() {
        return nameOfProduct;
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


    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
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

    public void setProduct(Product product) {
        this.product = product;
    }
}
