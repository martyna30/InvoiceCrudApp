package com.crud.invoices.service;

import com.crud.invoices.client.ViesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViesService {

    @Autowired
    ViesClient viesClient;

    public void isVATpayer() throws Exception {
        ViesClient.main();
    }
}
