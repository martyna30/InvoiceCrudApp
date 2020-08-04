package com.crud.invoices.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ITEMS")
public class Item {
    private Long id;
    Invoice invoice;
    private int quantity;
    private BigDecimal price;
    private BigDecimal value;

    public Item(Invoice invoice, int quantity, BigDecimal price, BigDecimal value) {
        this.invoice = invoice;
        this.quantity = quantity;
        this.price = price;
        this.value = value;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID")
    public Invoice getInvoice() {
        return invoice;
    }
    @Column
    public int getQuantity() {
        return quantity;
    }
    @Column
    public BigDecimal getPrice() {
        return price;
    }
    @Column
    public BigDecimal getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

}
