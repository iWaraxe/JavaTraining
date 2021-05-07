package by.issoft.store.helpers.comparators;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class XmlReader {

    private String configFilePath = "store\\src\\main\\resources\\config.xml";

    public Map<String, String> getAllPropertiesToSort() throws ParserConfigurationException, IOException, SAXException {
        String sortTag = "sort";
        Map<String, String> propertiesMap = new LinkedHashMap<>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(configFilePath);

            Node node = document.getElementsByTagName(sortTag).item(0);
            NodeList sortProperties = node.getChildNodes();

            Element elementary;
            for (int i = 0; i < sortProperties.getLength(); i++) {

                //get all child tag names and values from config file and add them in properties map
                if (sortProperties.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    elementary = (Element) sortProperties.item(i);

                    propertiesMap.put(elementary.getTagName().toLowerCase(Locale.ROOT), elementary.getTextContent().toUpperCase(Locale.ROOT));
                }
            }

        return propertiesMap;
    }
}
