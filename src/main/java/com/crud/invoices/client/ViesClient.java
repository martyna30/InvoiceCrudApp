package com.crud.invoices.client;

import org.springframework.stereotype.Component;

import javax.xml.soap.*;

@Component
public class ViesClient {
    public static void main() throws Exception {

        String viesEndpoint="http://ec.europa.eu/taxation_customs/vies/checkVatTestService";
        String action="";
        callSoapWebService(viesEndpoint, action);
    }
    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
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
        soapBodyElem1.addTextNode("PL"); //kraj vatu
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("vatNumber", nameSpace);
        soapBodyElem2.addTextNode("05006900962");//numer vat
    }

    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
}
