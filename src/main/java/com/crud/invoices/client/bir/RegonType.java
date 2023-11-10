package com.crud.invoices.client.bir;

import org.springframework.stereotype.Component;

@Component
public class RegonType {

    private String nip;

    public RegonType() {}


    public RegonType(String nip) {
       this.nip = nip;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
