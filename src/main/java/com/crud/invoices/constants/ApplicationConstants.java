package com.crud.invoices.constants;

import org.springframework.stereotype.Component;

@Component
public class ApplicationConstants {

    public static final String REGON_BROWSER_PAGE_ADDRESS =
            "https://wyszukiwarkaregon.stat.gov.pl/appBIR/index.aspx";

    public static final String BIR1_ADDRESS = "https://wyszukiwarkaregontest.stat.gov.pl/wsBIR/UslugaBIRzewnPubl.svc";//do szukaj podmioty
    public static final String BIR1_WSDL_ADDRESS = "https://wyszukiwarkaregontest.stat.gov.pl/wsBIR/wsdl/UslugaBIRzewnPubl.xsd";
    public static final String BIR1_USER_KEY = "abcde12345abcde12345";
    /*public static final List<String> CUSTOM_HEADER = ImmutableList.of(
            "regon9",
            "regon14",
            "nip",
            "nazwisko",
            "imie1",
            "imie2",
            "Nazwa",
            "nazwa"
    );*/

    private static String getChromedriver() {
        return ApplicationConstants.class.getClassLoader()
                .getResource("chromedriver").getFile();
    }

}
