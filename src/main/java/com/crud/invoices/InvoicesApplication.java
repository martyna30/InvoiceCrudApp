package com.crud.invoices;

import com.crud.invoices.client.bir.RegonType;
import com.crud.invoices.client.bir.ReportClient;
import com.crud.invoices.client.bir.Reports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class InvoicesApplication extends SpringBootServletInitializer {


    public static void main(String[] args) throws Exception {
        //boolean isVat = ViesClient.checkVat("IE6388047V");
        //System.out.println("\n\n" + isVat);

        // InvoicesApplication invoicesApplication = new InvoicesApplication();
        ReportClient reportClient = new ReportClient();
        Reports reports = reportClient.getReport(new RegonType("8992535351"));
        System.out.println(reports.getBasicData());
        SpringApplication.run(InvoicesApplication.class, args);

    }


}
