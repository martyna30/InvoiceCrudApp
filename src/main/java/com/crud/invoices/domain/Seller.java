package com.crud.invoices.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SELLER")
public class Seller {
    private Long id;
    private String name;
    private List<Invoice> invoices = new ArrayList<>();

    public Seller(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Seller(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }


    @Column
    public String getName() {
        return name;
    }

    @OneToMany(
            targetEntity = Invoice.class,
            mappedBy = "seller",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}

