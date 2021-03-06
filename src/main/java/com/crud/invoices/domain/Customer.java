package com.crud.invoices.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERS")
public class Customer {
    private Long id;
    private List<Invoice> invoices = new ArrayList<>();
    private String name;
    private double vatNumber;
    private boolean isVATpayer;

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, double vatNumber, boolean isVATpayer) {
        this.name = name;
        this.vatNumber = vatNumber;
        this.isVATpayer = isVATpayer;
    }

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "CUSTOMER_ID", unique = true)
    public Long getId() {
        return id;
    }


    @OneToMany(
            targetEntity = Invoice.class,
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Invoice> getInvoices() {
        return invoices;
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public double getVatNumber() {
        return vatNumber;
    }

    @Column
    public boolean isVATpayer() {
        return isVATpayer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVatNumber(double vatNumber) {
        this.vatNumber = vatNumber;
    }

    public void setVATpayer(boolean VATpayer) {
        isVATpayer = VATpayer;
    }


}
