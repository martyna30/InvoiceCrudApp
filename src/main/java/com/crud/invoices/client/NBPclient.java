package com.crud.invoices.client;

import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.nbp.config.NBPconfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class NBPclient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NBPclient.class);

    @Autowired
    NBPconfig nbPconfig;

    @Autowired
    private RestTemplate restTemplate;

    private ExchangeDto nbPtableDto;

    public List<ExchangeDto> getTable() {
        URI url =  UriComponentsBuilder.fromHttpUrl(nbPconfig.getNBPapiEndpoint() +  "/exchangerates/tables/A/?format=json")
                .build().encode().toUri();//qwery param?

        try {
            System.out.println(url);
            ExchangeDto[] tableResponse = restTemplate.getForObject(url, ExchangeDto[].class);
            System.out.println(tableResponse);
            return Arrays.asList(ofNullable(tableResponse).orElse(new ExchangeDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
