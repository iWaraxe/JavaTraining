package by.issoft.store.helpers.comparator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class XmlReader {

    public ArrayList<String> getAllPropertiesToSort() throws ParserConfigurationException, IOException, SAXException {

        String path = "store\\src\\main\\resources\\config.xml";
        String sortTag = "sort";
        ArrayList<String> properties = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(path);

        Node node = document.getElementsByTagName(sortTag).item(0);
        NodeList sortProperties = node.getChildNodes();

        Element elementary;
        for (int i = 0; i < sortProperties.getLength(); i++) {

            if (sortProperties.item(i).getNodeType() == Node.ELEMENT_NODE) {
                elementary = (Element) sortProperties.item(i);

                properties.add(elementary.getTagName());
            }
        }
        return properties;
    }

    public SortOrder getSortOrderByXmlProperty(String propertyName) throws ParserConfigurationException, IOException, SAXException {

        String path = "store\\src\\main\\resources\\config.xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(path);
        String propertyValue = document.getElementsByTagName(propertyName).item(0).getTextContent();

        SortOrder sortOrder = SortOrder.valueOf(propertyValue.toUpperCase(Locale.ROOT));

        return sortOrder;
    }
}
