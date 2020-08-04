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
    private List<Customer> customers = new ArrayList<>();
    private double netto;
    private double brutto;
    private LocalDate dateOfInvoice;
    private LocalDate dateOfPayment;
    boolean isPaid;

    public Invoice(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Invoice(String number) {
        this.number = number;
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

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "invoices")
            //{CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "invoices")
    public List<Customer> getCustomers() {
        return customers;
    }

    @Column
    public double getNetto() {
        return netto;
    }

    @Column
    public double getBrutto() {
        return brutto;
    }

    @Column
    public LocalDate getDateOfInvoice() {
        return dateOfInvoice;
    }

    @Column
    public LocalDate getDateOfPayment () {
        return dateOfPayment;
    }

    @Column
    public boolean isPaid () {
            return isPaid;
    }

    public void setId (Long id){
            this.id = id;
    }

    public void setNumber (String number){
        this.number = number;
    }

    public void setItems (List < Item > items) {
        this.items = items;
    }

    public void setCustomers (List < Customer > customers) {
        this.customers = customers;
    }

    public void setNetto ( double netto){
            this.netto = netto;
    }
    public void setBrutto ( double brutto){
            this.brutto = brutto;
    }

    public void setPaid ( boolean paid){
        isPaid = paid;
    }

    public void setDateOfInvoice (LocalDate dateOfInvoice){
        this.dateOfInvoice = dateOfInvoice;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
}


