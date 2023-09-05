package com.crud.invoices.client.bir;


import cis.bir.publ._2014._07.datacontract.ObjectFactory;
import cis.bir.publ._2014._07.datacontract.ParametryWyszukiwania;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tempuri.IUslugaBIRzewnPubl;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReportClient {

    @Autowired
    RestTemplate restTemplate;

    private BirClient birClient;
    private RegonType regonType;


    public ReportClient(BirClient birClient, RegonType regonType) {
        this.birClient = birClient;
        this.regonType = regonType;

    }

    public synchronized String getReport(RegonType regonType) {

        String basicData;

        try {
            final IUslugaBIRzewnPubl port = birClient.prepareApi();

             ObjectFactory objectFactory = new ObjectFactory();
             JAXBElement<String> nipParam = objectFactory.createParametryWyszukiwaniaNip(regonType.getNip());
             ParametryWyszukiwania parametryWyszukiwania = new ParametryWyszukiwania();
             parametryWyszukiwania.setNip(nipParam);
             basicData = port.daneSzukajPodmioty(parametryWyszukiwania);

            //final String silosId = getSilosId(basicData);
            //String additionalReport = (silosId != null)?
            // String additionalReport = (silosId != null && regonType.getReportType().equals("F")) ?
            //port.danePobierzPelnyRaport(regonType.getRegon(), getReportNameForGivenSilosId(silosId)) : null;
            //if (silosId != null) {
                //reports = new Reports(basicData);
            //}
        }//reports = new Reports(generalReport, pkdReport, basicData, additionalReport);
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return basicData;
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
