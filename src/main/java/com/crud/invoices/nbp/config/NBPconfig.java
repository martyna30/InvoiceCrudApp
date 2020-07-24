package com.crud.invoices.nbp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NBPconfig {
    @Value("${nbp.api.endpoint.prod}")
    private String NBPapiEndpoint;
}
