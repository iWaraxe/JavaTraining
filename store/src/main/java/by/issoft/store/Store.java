package by.issoft.store;

import by.issoft.domain.Category;
import by.issoft.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class Store {

    public List<Category> categoryList = new ArrayList<>();

    public void printAllCategoriesAndProduct() {

        for (Category category : categoryList) {
            category.printAllProduct();
        }
    }

    public void printListProducts(List<Product> products) {

        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    public List<Product> getListOfAllProducts() {

        List<Product> allProducts = new ArrayList<>();

        for (Category category : this.categoryList) {
            allProducts.addAll(category.productList);
        }

        return allProducts;
    }
}
