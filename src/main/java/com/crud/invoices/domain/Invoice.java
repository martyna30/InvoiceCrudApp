package com.crud.invoices.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "INVOICES")
public class Invoice {
    private Long id;
    private String number;

    private Contractor contractor;
    private LocalDate dateOfInvoice;
    private LocalDate dateOfSale;

    private LocalDate dateOfPayment;//usun

    private String periodOfPayment;

    private String methodOfPayment;
    private BigDecimal paid;

    private BigDecimal amountPaid;
   // private List<Payment> payments = new ArrayList<>();
    private BigDecimal leftToPay;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus isSettled = InvoiceStatus.FALSE;
    private List<Item> items = new ArrayList<>();
    private BigDecimal netAmount;
    private BigDecimal sumTotal;
    private BigDecimal amountOfVAT;


    public Invoice(Contractor contractor) {
     this.contractor = contractor;
    }

    public Invoice(Long id,Contractor contractor, LocalDate dateOfInvoice, LocalDate dateOfSale,
                   String periodOfPayment, String methodOfPayment, BigDecimal paid,List<Item> items,
                   BigDecimal netAmount, BigDecimal sumTotal) {
        this.id = id;
        this.contractor = contractor;
        this.dateOfInvoice = dateOfInvoice;
        this.dateOfSale = dateOfSale;
        this.periodOfPayment = periodOfPayment;
        this.methodOfPayment = methodOfPayment;
        this.paid = paid;
        this.items = items;
        this.netAmount = netAmount;
        this.sumTotal = sumTotal;
    }

    public Invoice(Long id, String number,Contractor contractor, LocalDate dateOfInvoice, LocalDate dateOfSale,
                   LocalDate dateOfPayment, String periodOfPayment,
                   String methodOfPayment, BigDecimal paid, List<Item> items, BigDecimal amountPaid,
                   BigDecimal leftToPay, InvoiceStatus isSettled, BigDecimal netAmount,
                   BigDecimal sumTotal,BigDecimal amountOfVAT) {
        this.id = id;
        this.number = number;
        this.contractor = contractor;
        this.dateOfInvoice = dateOfInvoice;
        this.dateOfSale = dateOfSale;
        this.dateOfPayment = dateOfPayment;
        this.periodOfPayment = periodOfPayment;
        this.methodOfPayment = methodOfPayment;
        this.paid = paid;
        this.amountPaid = amountPaid;
        this.leftToPay = leftToPay;
        this.isSettled = isSettled;
        this.items = items;
        this.netAmount = netAmount;
        this.sumTotal = sumTotal;
        this.amountOfVAT = amountOfVAT;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column
    public String getNumber() {
        return number;
    }



   @OneToMany(
            cascade = CascadeType.ALL
   )
   @JoinColumn(name = "INVOICE_ID")
   public List<Item> getItems() {
        return items;
    }

    @JoinColumn(name = "CONTRACTOR_ID")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    public Contractor getContractor() {
        return contractor;
    }

    //@JoinColumn(name = "SELLER_ID")
   // @ManyToOne

    @Column
    public LocalDate getDateOfInvoice() {
        return dateOfInvoice;
    }


    @Column
    public LocalDate getDateOfSale() {
        return dateOfSale;
    }


    @Column
    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }



    @Column
    public String getPeriodOfPayment() {
        return periodOfPayment;
    }

    @Column
    public String getMethodOfPayment() {
        return methodOfPayment;
    }

    @Column
    public BigDecimal getPaid() {
        return paid;
    }

    @Column
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    /*@OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "INVOICE_ID")*/

    @Column
    public BigDecimal getLeftToPay() {
        return leftToPay;
    }



    @Column
    public InvoiceStatus getIsSettled() {
        return isSettled;
    }

    @Column
    public BigDecimal getNetAmount() {
        return netAmount;
    }
    @Column
    public BigDecimal getSumTotal() {
        return sumTotal;
    }
    @Column
    public BigDecimal getAmountOfVAT() {
        return amountOfVAT;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }


    public void setLeftToPay(BigDecimal leftToPay) {
        this.leftToPay = leftToPay;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public void setDateOfInvoice(LocalDate dateOfInvoice) {
        this.dateOfInvoice = dateOfInvoice;
    }


    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public void setPeriodOfPayment(String periodOfPayment) {
        this.periodOfPayment = periodOfPayment;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public void setSumTotal(BigDecimal sumTotal) {
        this.sumTotal = sumTotal;
    }

    public void setAmountOfVAT(BigDecimal amountOfVAT) {
        this.amountOfVAT = amountOfVAT;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }


    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setIsSettled(InvoiceStatus isSettled) {
        this.isSettled = isSettled;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}



