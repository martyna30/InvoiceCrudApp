package com.crud.invoices.client.bir;


import com.crud.invoices.client.bir.constants.ApplicationConstants;
import com.crud.invoices.client.bir.handler.SoapHandlerResolver;
import com.crud.invoices.client.bir.handler.SoapMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.tempuri.IUslugaBIRzewnPubl;
import org.tempuri.UslugaBIRzewnPubl;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.AddressingFeature;
import java.net.MalformedURLException;

import static com.crud.invoices.client.bir.constants.ApplicationConstants.BIR1_ADDRESS;


@Component
public class BirClient extends WebServiceGatewaySupport {

    private final String SESSION_STATUS = "StatusSesji";

    private SoapMessageHandler soapMessageHandler;

    private SoapHandlerResolver soapHandlerResolver;

    public BirClient() {
        this.soapMessageHandler = new SoapMessageHandler();
        this.soapHandlerResolver = new SoapHandlerResolver(soapMessageHandler);
    }

    private static final Logger log = LoggerFactory.getLogger(BirClient.class);

    public IUslugaBIRzewnPubl prepareApi() throws MalformedURLException {
        IUslugaBIRzewnPubl port = preparePort();

        final String sessionStatus = port.getValue(SESSION_STATUS);

        if (this.soapMessageHandler.getSessionCookie().equals("") || sessionStatus.equals("1")) {
            String sid = port.zaloguj(ApplicationConstants.BIR1_USER_KEY);
            this.soapMessageHandler.setSessionCookie(sid);
        }

        return port;
    }

    public void logout() throws MalformedURLException {
        final IUslugaBIRzewnPubl port = preparePort();
        port.wyloguj(soapMessageHandler.getSessionCookie());
    }

    public SoapMessageHandler getSoapMessageHandler() {
        return soapMessageHandler;
    }

    public SoapHandlerResolver getSoapHandlerResolver() {
        return soapHandlerResolver;
    }

    private IUslugaBIRzewnPubl preparePort() throws MalformedURLException {
        UslugaBIRzewnPubl service  = new UslugaBIRzewnPubl();
        service.setHandlerResolver(this.soapHandlerResolver);


        IUslugaBIRzewnPubl port = service.getE3(new AddressingFeature());

        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, BIR1_ADDRESS);
        return port;
    }

/*
    UslugaBIRzewnPubl service = new UslugaBIRzewnPubl();
    private SoapHandlerResolver soapHandlerResolver;
    service.setHandlerResolver(new SoapHandlerResolver()); //doklejanieSID'a do HTTP HEADER


    IUslugaBIRzewnPubl port = service.getE3(new AddressingFeature());
    String statusUslugi = "StatusUslugi";
    String result = port.getValue(statusUslugi);
// logowanie, jezeli sesja wygasla, badx logowanie pierwszy raz


    if ((SoapMessageHandler.sessionCookie.equals("")) || (!
        result.equals("1"))){
        String sid = port.zaloguj("abcde12345abcde12345");
        SoapMessageHandler.sessionCookie = sid;
    }
// przykład wyszukiwania po NIPie
    ObjectFactory objectFactory = new ObjectFactory();
    JAXBElement<String> nipParam =
            objectFactory.createParametryWyszukiwaniaNip("1234567890");
    ParametryWyszukiwania parametryWyszukiwania = new ParametryWyszukiwania();
    parametryWyszukiwania.setNip(nipParam);
    String raport = port.daneSzukaj(parametryWyszukiwania);


    public BIRclient() throws SOAPException, MalformedURLException {
    }*/
}
