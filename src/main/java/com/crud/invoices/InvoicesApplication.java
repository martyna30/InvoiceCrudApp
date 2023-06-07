package com.crud.invoices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class InvoicesApplication extends SpringBootServletInitializer {


    public static void main(String[] args) throws Exception {
        //boolean isVat = ViesClient.checkVat("IE6388047V");
        //System.out.println("\n\n" + isVat);

        // InvoicesApplication invoicesApplication = new InvoicesApplication();


        SpringApplication.run(InvoicesApplication.class, args);

    }


}
