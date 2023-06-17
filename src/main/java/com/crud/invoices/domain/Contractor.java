package com.crud.invoices.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Entity
@Table(name = "CONTRACTOR")
public class Contractor {
    private Long id;
    private String name;
    private String vatIdentificationNumber;

    Address address;


    public Contractor(Long id, String name, String vatIdentificationNumber, Address address) {
        this.id = id;
        this.name = name;
        this.vatIdentificationNumber = vatIdentificationNumber;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTRACTOR_ID", unique = true)
    public Long getId() {
        return id;
    }


   // @OneToMany(
            //targetEntity = Invoice.class,
            //mappedBy = "customer",
            //cascade = CascadeType.ALL
            //fetch = FetchType.LAZY
    //)

    //public List<Invoice> getInvoices() {
        //return invoices;
   // }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ADRESS_ID", unique = true) //
    public Address getAddress() {
        return address;
    }

    @Column
    public String getName() {
        return name;
    }



    @Column
    public String getVatIdentificationNumber() {
        return vatIdentificationNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setVatIdentificationNumber(String vatIdentificationNumber) {
        this.vatIdentificationNumber = vatIdentificationNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
