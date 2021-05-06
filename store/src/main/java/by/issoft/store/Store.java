package by.issoft.store;

import by.issoft.domain.Category;
import by.issoft.domain.Product;

import java.util.ArrayList;

public class Store {

    public ArrayList<Category> categoryList = new ArrayList<>();

    public void printAllCategoriesAndProduct() {

        for (Category category : categoryList) {
            category.printAllProduct();
        }
    }

    public void printListProducts(ArrayList<Product> products) {

        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    public ArrayList<Product> getListOfAllProducts() {

        ArrayList<Product> allProducts = new ArrayList<Product>();

        for (Category category : this.categoryList) {
            allProducts.addAll(category.productList);
        }

        return allProducts;
    }
}
