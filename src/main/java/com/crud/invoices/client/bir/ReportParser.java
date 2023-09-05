package com.crud.invoices.client.bir;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
@Component
public class ReportParser {

    private final String DATA_MARKUP_NAME = "dane";

    public Map<String, String> parseReport(String report) throws Exception {
        Map<String, String> result = new HashMap<>();

        Document basicData = loadXMLFromString(report);
        System.out.println(basicData);
        result.putAll(parseBasicData(basicData));

        return result;
    }

    private Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);

    }



    private Map<String, String> parseBasicData(Document report) {
        Map<String, String> result = new HashMap<>();

        final NodeList dataNodes = report.getElementsByTagName(DATA_MARKUP_NAME);

        for (int i = 0; i < dataNodes.getLength(); i++) {
            Node item = dataNodes.item(i).getChildNodes().item(0);
            while (item != null) {
                result.merge(item.getNodeName(), item.getTextContent(), mergeValues());
                item = item.getNextSibling();
            }
        }

        return result;
    }

   /* private Map<String, String> parseBasic(Document report) {
        Map<ContractorFromGusDto, String> result = new HashMap<>();

        final NodeList dataNodes = report.getElementsByTagName(DATA_MARKUP_NAME);

        for (int i = 0; i < dataNodes.getLength(); i++) {
            Node item = dataNodes.item(i).getChildNodes().item(0);
            while (item != null) {
                result.merge(item.getNodeName(), item.getTextContent(), mergeValues());
                item = item.getNextSibling();
            }
        }

        return result;
    }*/

    private BiFunction<? super String, ? super String, ? extends String> mergeValues() {
        return (oldValue, newValue) -> oldValue.contains(newValue) ? oldValue : oldValue + " " + newValue;
    }
}
