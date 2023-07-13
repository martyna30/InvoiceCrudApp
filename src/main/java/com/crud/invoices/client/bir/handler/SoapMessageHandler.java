package com.crud.invoices.client.bir.handler;

import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.*;
@Component
public class SoapMessageHandler implements SOAPHandler<SOAPMessageContext> {

    private final String SID = "sid";

    private String sessionCookie;

    public String getSessionCookie() {
        return sessionCookie;
    }

    public SoapMessageHandler() {
        this.sessionCookie = "";
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if ((boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
            if (headers == null) {
                headers = new HashMap<>();
                context.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            }
            List<String> cookies = headers.computeIfAbsent(SID, k -> new ArrayList<>());
            cookies.add(this.sessionCookie);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }


}
