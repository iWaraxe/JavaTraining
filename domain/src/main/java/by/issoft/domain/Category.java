package by.issoft.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {
    public String name;
    public List<Product> productList;

    public Category(String name) {

        this.name = name;
    }

    public void addProduct(Product product) {
        if (productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(product);
    }

    public void printAllProduct() {

        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
        System.out.println("Category " + name + " : ");
        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));

        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }
}
