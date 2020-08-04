package com.crud.invoices.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQuery(
        name = "Exchange.retrieveTableWithSpecifiedDate",
        query = "FROM Exchange WHERE date = :EFFECTIVE_DATE"
)

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXCHANGE")
public class Exchange {
    private Long id;
    private String name;
    private List<Rate> rates = new ArrayList();
    private LocalDate date;

    public Exchange(String name, List<Rate> rates, LocalDate date) {
        this.name = name;
        this.rates = rates;
        this.date = date;
    }

    public Exchange(String name, List<Rate> rates) {
        this.name = name;
        this.rates = rates;
    }

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }


    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    @OneToMany(
            targetEntity = Rate.class,
            mappedBy = "exchange",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Rate> getRates() {
        return rates;
    }

    @Column(name = "EFFECTIVE_DATE")
    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}






