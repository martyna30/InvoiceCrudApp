package com.crud.invoices.client;

import javax.xml.soap.*;

//@Component
public class ViesClient {

    private static String endpoint = "http://ec.europa.eu/taxation_customs/vies/services/checkVatService";

    public static boolean checkVat(String vatNumber) throws Exception {
        SOAPMessage soapMessage = createSOAPMessage(vatNumber);
        return callSoapWebService(soapMessage);
    }

    public static SOAPMessage createSOAPMessage(String vatNumber) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSOAPMessageEnvelope(soapMessage, vatNumber);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "");

        soapMessage.saveChanges();
        //Print the request message, just for debugging purposes
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    public static void createSOAPMessageEnvelope(SOAPMessage soapMessage, String vatNumber) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String nameSpace = "urn";
        String nameSpaceURI = "urn:ec.europa.eu:taxud:vies:services:checkVat:types";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(nameSpace, nameSpaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("checkVat", nameSpace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("countryCode", nameSpace);
        soapBodyElem1.addTextNode(vatNumber.substring(0, 2)); // kraj vatu
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("vatNumber", nameSpace);
        soapBodyElem2.addTextNode(vatNumber.substring(2)); // numer vat
    }

    public  static boolean callSoapWebService(SOAPMessage soapMessage) throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(soapMessage, endpoint);
        soapConnection.close();

        // Print the SOAP Response
        System.out.println("Response SOAP Message:");
        soapResponse.writeTo(System.out);

        return soapResponse
                .getSOAPBody()
                .getElementsByTagName("valid")
                .item(0)
                .getTextContent()
                .equals("true");
    }

}

