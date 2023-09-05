package com.crud.invoices.fasade;

import com.crud.invoices.mapper.ContractorMapper;
import com.crud.invoices.service.BirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BirFasade {
    private static final Logger LOGGER = LoggerFactory.getLogger(BirFasade.class);

    @Autowired
    BirService birService;
    @Autowired
    ContractorMapper contractorMapper;


}
