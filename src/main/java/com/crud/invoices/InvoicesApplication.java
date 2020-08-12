package com.crud.invoices;

import com.crud.invoices.client.ViesClient;
import com.crud.invoices.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class InvoicesApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        boolean isVat = ViesClient.checkVat("IE6388047V");
        System.out.println("\n\n" + isVat);

        SpringApplication.run(InvoicesApplication.class, args);



    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(InvoicesApplication.class);
    }
}
