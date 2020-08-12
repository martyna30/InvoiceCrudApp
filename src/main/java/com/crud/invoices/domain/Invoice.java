package com.crud.invoices.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INVOICES")
public class Invoice {
    private Long id;
    private String number;
    private List<Item> items = new ArrayList<>();
    private Customer customer;
    private LocalDate dateOfInvoice;
    private LocalDate dateOfPayment;
    InvoiceStatus isPaid;
    private double netto;
    private double brutto;

    public Invoice(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Invoice(String number) {
        this.number = number;
    }

    public Invoice(Long id, String number, List<Item> items, InvoiceStatus isPaid, double netto, double brutto) {
        this.id = id;
        this.number = number;
        this.items = items;
        this.brutto = brutto;
        this.isPaid = isPaid;
        this.netto = netto;
    }

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "INVOICE_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column
    public String getNumber() {
        return number;
    }

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Item> getItems() {
        return items;
    }

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    @Column
    public LocalDate getDateOfInvoice() {
        return dateOfInvoice;
    }

    @Column
    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    @Column
    public InvoiceStatus getIsPaid() {
        return isPaid;
    }

    public double getNetto() {
        return netto;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setIsPaid(InvoiceStatus isPaid) {
        this.isPaid = isPaid;
    }

    public void setDateOfInvoice(LocalDate dateOfInvoice) {
        this.dateOfInvoice = dateOfInvoice;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }
}


