package com.crud.invoices.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name="PRODUCT")
public class Product {
    private Long id;
    private String nameOfProduct;
    //Invoice invoice;
    private String type;
    private String unit;
    private String code;
    private BigDecimal netWorth;
    private Integer vatRate;
    private BigDecimal grossValue;
    //private List<Item> productsItems =  new ArrayList<>();

    public Product(Long id, String nameOfProduct,String type, String unit,
                   String code, BigDecimal netWorth,
                   Integer vatRate, BigDecimal grossValue) {
        this.id = id;
        this.nameOfProduct = nameOfProduct;
        this.type = type;
        this.unit = unit;
        this.code = code;
        this.netWorth = netWorth;
        this.vatRate = vatRate;
        this.grossValue = grossValue;

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PRODUCT_ID",unique = true)
    public Long getId() {
        return id;
    }

    /*@OneToMany(
          cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "PRODUCT_ID")
    public List<Item> getProductsItems() {
        return productsItems;
    }*/


    @Column
    public String getCode() {
        return code;
    }
    @Column
    public String getNameOfProduct() {
        return nameOfProduct;
    }

    @Column
    public String getUnit() {
        return unit;
    }

    public String getType() {
        return type;
    }


    @Column
    public BigDecimal getNetWorth() {
        return netWorth;
    }
    @Column
    public Integer getVatRate() {
        return vatRate;
    }
    @Column
    public BigDecimal getGrossValue() {
        return grossValue;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public void setCode(String code) {
        this.code = code;
    }


    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }

    public void setVatRate(Integer vatRate) {
        this.vatRate = vatRate;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }


    public void setType(String type) {
        this.type = type;
    }


}
