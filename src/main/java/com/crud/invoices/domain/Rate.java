package com.crud.invoices.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name = "RATES")
public class Rate {
    private Long id;
    //Exchange exchange;
    private String currency;
    private BigDecimal rateOfExchange;

    public Rate(Long id, String currency, BigDecimal rateOfExchange) {
        this.id = id;
        this.currency = currency;
        this.rateOfExchange = rateOfExchange;
    }

    public Rate(String currency, BigDecimal rateOfExchange) {
        this.currency = currency;
        this.rateOfExchange = rateOfExchange;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }

    //@ManyToOne
    //@JoinColumn(name = "EXCHANGE_ID")
    //public Exchange getExchange() {
    // return exchange;
    //}

    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    @Column(name = "RATE_OF_EXCHANGE", precision = 19, scale = 4)
    public BigDecimal getRateOfExchange() {
        return rateOfExchange;
    }

    public void setCurrency(String code) {
        this.currency = code;
    }

    public void setRateOfExchange(BigDecimal rateOfExchange) {
        this.rateOfExchange = rateOfExchange;
    }

    public void setId(Long id) {
        this.id = id;
    }


}



