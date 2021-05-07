package by.issoft.store.helpers.comparators;

import by.issoft.domain.Product;

import java.util.Comparator;

public class ProductPriceComparator  implements Comparator<Product> {
    SortOrder sortOrder;

    public ProductPriceComparator(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Product a, Product b) {

        if (sortOrder == SortOrder.ASC) {
            return a.price.compareTo(b.price);
        } else {
            return b.price.compareTo(a.price);
        }
    }
}