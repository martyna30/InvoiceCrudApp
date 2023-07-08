package com.crud.invoices.client.bir;

import org.springframework.stereotype.Component;

@Component
public class RegonType {

    /*private final Map<String, ReportNames> REPORTS_DEPENDENCE = ImmutableMap.of(
            "F", new ReportNames("PublDaneRaportFizycznaOsoba", "PublDaneRaportDzialalnosciFizycznej"),
            "LF", new ReportNames("PublDaneRaportLokalnaFizycznej", "PublDaneRaportDzialalnosciLokalnejFizycznej"),
            "P", new ReportNames("PublDaneRaportPrawna", "PublDaneRaportDzialalnosciPrawnej"),
            "LP", new ReportNames("PublDaneRaportLokalnaPrawnej", "PublDaneRaportDzialalnosciLokalnejPrawnej")
    );*/

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
