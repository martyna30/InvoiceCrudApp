package com.crud.invoices.domain;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT")
public class Payment {
    private Long id;
    private String methodOfPayment;
    private BigDecimal paid;
    private LocalDate dateOfPayment;
    Invoice invoice;


    public Payment(Long id, String methodOfPayment, BigDecimal paid, LocalDate dateOfPayment) {
        this.id = id;
        this.methodOfPayment = methodOfPayment;
        this.paid = paid;
        this.dateOfPayment = dateOfPayment;
    }
    public Payment(Long id, String methodOfPayment, BigDecimal paid, LocalDate dateOfPayment,Invoice invoice) {
        this.id = id;
        this.methodOfPayment = methodOfPayment;
        this.paid = paid;
        this.dateOfPayment = dateOfPayment;
        this.invoice = invoice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID", unique = true)
    public Long getId() {
        return id;
    }


    @JoinColumn(name = "INVOICE_ID")
    @ManyToOne(cascade = CascadeType.MERGE)
    public Invoice getInvoice() {
        return invoice;
    }

    @Column
    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }
    @Column
    public String getMethodOfPayment() {
        return methodOfPayment;
    }
    @Column
    public BigDecimal getPaid() {
        return paid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
