package com.crud.invoices.config;

import com.crud.invoices.client.bir.BirClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Bir1Configuration {



    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths("cis.bir._2014._07",
                "cis.bir.publ._2014._07",
                "cis.bir.publ._2014._07.datacontract");
        return marshaller;
    }

    @Bean
    public BirClient quoteClient(Jaxb2Marshaller marshaller) {
        BirClient birClient = new BirClient();
        birClient.setDefaultUri("http://www.webservicex.com/stockquote.asmx"); //TODO
        birClient.setMarshaller(marshaller);
        birClient.setUnmarshaller(marshaller);
        return birClient;
    }


}
