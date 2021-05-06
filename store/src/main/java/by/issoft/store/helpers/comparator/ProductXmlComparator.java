package by.issoft.store.helpers.comparator;

import by.issoft.domain.Product;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {

    public ArrayList<String> compareTo;
    SortOrder sortOrder;

    public ProductComparator(ArrayList<String> compareTo){
     this.compareTo = compareTo;
    }

    @Override
    public int compare(Product a, Product b) {

        XmlReader xml = new XmlReader();
        CompareToBuilder compareBuilder = new CompareToBuilder();

        try {
            for (String property : compareTo) {
                sortOrder = xml.getSortOrderByXmlProperty(property);

                switch (property) {
                    case "name":
                        if (sortOrder == SortOrder.ASC) {
                            compareBuilder.append(a.name, b.name);
                        } else {
                            compareBuilder.append(b.name, a.name);
                        }
                        break;

                    case "price":
                        if (sortOrder == SortOrder.ASC) {
                            compareBuilder.append(a.price, b.price);
                        } else {
                            compareBuilder.append(b.price, a.price);
                        }
                        break;

                    case "rate":
                        if (sortOrder == SortOrder.ASC) {
                            compareBuilder.append(a.rate, b.rate);
                        } else {
                            compareBuilder.append(b.rate, a.rate);
                        }
                        break;
                    default:
                        System.out.println("Can't perform sorting. Undefined property" + property);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return compareBuilder.toComparison();
    }
}
