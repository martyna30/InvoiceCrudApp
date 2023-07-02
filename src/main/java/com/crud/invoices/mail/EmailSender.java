package com.crud.invoices.mail;

public interface EmailSender {
    void send(String to, String text);
}
