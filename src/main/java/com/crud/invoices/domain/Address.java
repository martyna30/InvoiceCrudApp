package com.crud.invoices.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADDRESS")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID", unique = true)
    private Long id;

    private String street;

    private String streetNumber;

    private String postcode;

    private String city;

    private String country;

    @Column
    public String getStreet() {
        return street;
    }
    @Column
    public String getStreetNumber() {
        return streetNumber;
    }
    @Column
    public String getPostcode() {
        return postcode;
    }
    @Column
    public String getCity() {
        return city;
    }


    @Column
    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
