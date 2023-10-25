package com.crud.invoices.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${admin.mail}")
    private String adminMail;

    @Value("martyna.prawdzik@gmail.com")
    private String adminMail2;
}
