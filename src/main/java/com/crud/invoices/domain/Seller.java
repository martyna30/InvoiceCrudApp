package com.crud.invoices.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "SELLER")
public class Seller {
    private Long id;

    private String name;

    private double vatIdentificationNumber;

    List<Invoice> invoiceList = new ArrayList<>();

    Address address;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SELLER_ID", unique = true)
    public Long getId() {
        return id;
    }
    @Column
    public String getName() {
        return name;
    }
    @Column
    public double getVatIdentificationNumber() {
        return vatIdentificationNumber;
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    public Address getAddress() {
        return address;
    }
    @OneToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "SELLER_ID")
    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVatIdentificationNumber(double vatIdentificationNumber) {
        this.vatIdentificationNumber = vatIdentificationNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
}
