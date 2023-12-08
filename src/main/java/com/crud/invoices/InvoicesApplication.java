package com.crud.invoices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class InvoicesApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
      // String dest = "/home/martyna/Pulpit/pdfPlik.plik.pdf";
      // String data ="Jan";
       //PrinterService printer = new PrinterService();
       //printer.generatePDF(data,dest);
        //boolean isVat = ViesClient.checkVat("IE6388047V");
        //System.out.println("\n\n" + isVat);
        //8992535351 nM
        /*BirClient birClient = new BirClient();
        RestTemplate restTemplate = new RestTemplate();
        IUslugaBIRzewnPubl port = birClient.prepareApi();
        //String sid = port.zaloguj("abcde12345abcde12345");
        //System.out.println(sid);
        RegonType regonType = new RegonType();
        ContractorFromGusDto contractorFromGusDto = new ContractorFromGusDto();
        regonType.setNip("5741739416");
        ReportClient reportClient = new ReportClient(birClient, regonType);
        String basic = reportClient.getReport(regonType);
        ReportParser reportParser = new ReportParser();
        Map<String, String> map = reportParser.parseReport(basic);
        for(Map.Entry<String, String> maps : map.entrySet()) {
            if (maps.getKey().contains("Nazwa")) {
                contractorFromGusDto.setName(maps.getValue());
                System.out.println(contractorFromGusDto.getName());
            }
            if (maps.getKey().contains("Nip")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }
            if (maps.getKey().contains("Ulica")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("NrNieruchomosci")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("KodPocztowy")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("Miejscowosc")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }
        }*/

            //String nazwa = map.get("Nazwa").toString();


            SpringApplication.run(InvoicesApplication.class, args);

        }


    }

