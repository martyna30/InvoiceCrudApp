package com.crud.invoices.client.bir;

import cis.bir.publ._2014._07.IUslugaBIRzewnPubl;
import cis.bir.publ._2014._07.datacontract.ObjectFactory;
import cis.bir.publ._2014._07.datacontract.ParametryWyszukiwania;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class ReportClient {

    private BirClient birClient;

    RegonType regonType;

    public ReportClient() {
        this.birClient = new BirClient();
    }

    public synchronized Reports getReport(RegonType regonType) {
        Reports reports = null;

        try {
            final IUslugaBIRzewnPubl port = birClient.prepareApi();

            ObjectFactory objectFactory = new ObjectFactory();
            ParametryWyszukiwania searchParams = objectFactory.createParametryWyszukiwania();
            searchParams.setNip(objectFactory.createParametryWyszukiwaniaNip(regonType.getNip()));
            // searchParams.setRegon(objectFactory.createParametryWyszukiwaniaRegon(regonType.getRegon()));

            // String generalReport = port.danePobierzPelnyRaport(regonType.getRegon(),
            // regonType.getReportName().getGeneralReportName());
            //String pkdReport = port.danePobierzPelnyRaport(regonType.getRegon(),
            // regonType.getReportName().getPkdReportName());
            String basicData = port.daneSzukaj(searchParams);

            final String silosId = getSilosId(basicData);
            //String additionalReport = (silosId != null)?
            // String additionalReport = (silosId != null && regonType.getReportType().equals("F")) ?
            //port.danePobierzPelnyRaport(regonType.getRegon(), getReportNameForGivenSilosId(silosId)) : null;
            if (silosId != null) {
                reports = new Reports(basicData);
            }
        }//reports = new Reports(generalReport, pkdReport, basicData, additionalReport);
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return reports;
    }

    public void logout() {
        try {
            birClient.logout();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    private String getSilosId(final String basicDataReport) {
        Pattern pattern = Pattern.compile("<SilosID>(.*)</SilosID>");
        Matcher matcher = pattern.matcher(basicDataReport);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String getReportNameForGivenSilosId(final String silosId) {
        switch (silosId) {
            case "1":
                return "PublDaneRaportDzialalnoscFizycznejCeidg";
            case "2":
                return "PublDaneRaportDzialalnoscFizycznejRolnicza";
            case "3":
                return "PublDaneRaportDzialalnoscFizycznejPozostala";
            case "4":
                return "PublDaneRaportDzialalnoscFizycznejWKrupgn";
            default:
                return "PublDaneRaportLokalneFizycznej";
        }
    }
}
