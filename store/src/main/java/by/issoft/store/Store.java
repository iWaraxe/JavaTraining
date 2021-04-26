package by.issoft.store;

import by.issoft.domain.Category;

import java.util.ArrayList;

public class Store {

    public ArrayList<Category> categoryList = new ArrayList<>();

    public void printAllCategoriesAndProduct()
    {
        for (Category category: categoryList) {
            category.printAllProduct();
        }
    }
}
