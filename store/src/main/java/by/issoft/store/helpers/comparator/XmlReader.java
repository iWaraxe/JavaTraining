package by.issoft.store.helpers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Locale;

public class XmlReader {

    public ProductComparator.SortOrder getSortOrderByProperty(String propertyName) throws ParserConfigurationException, IOException, SAXException {

        String path = "store\\src\\main\\resources\\config.xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(path);
        String propertyValue = document.getElementsByTagName(propertyName).item(0).getTextContent();

        ProductComparator.SortOrder sortOrder = ProductComparator.SortOrder.valueOf(propertyValue.toUpperCase(Locale.ROOT));

        return sortOrder;
    }
}
