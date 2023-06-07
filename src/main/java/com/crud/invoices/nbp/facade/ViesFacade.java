package com.crud.invoices.nbp.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViesFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeFacade.class);

    @Autowired
    private ViesFacade viesFacade;

    //@Autowired
    //private ViesMapper viesMapper;
}
