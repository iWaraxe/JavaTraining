package by.issoft.store.helpers.populators;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import by.issoft.store.helpers.DBManager;
import by.issoft.store.helpers.IDBManager;

import java.sql.*;
import java.util.List;

public class DBPopulator implements IPopulator {

    public IDBManager dbManager;

    public DBPopulator(){

        this.dbManager = new DBManager();
    }

    public DBPopulator(IDBManager dbManager) {

        this.dbManager = dbManager;
    }

    @Override
    public List<Category> getCategories() {

        try {
            if (dbManager.createTableIfDoesNotExist()) {
                fillDbByFaker();
            }

            return dbManager.getAllCategories();

        } catch (Exception ex) {
            System.out.println("Exception was thrown in getCategories(). Message: " + ex.getLocalizedMessage());
            dbManager.dispose();

            return (new RandomStorePopulator()).getCategories();
        }
    }

    @Override
    public List<Product> getProductsForCategory(CategoryEnum categoryName) {

        try {
            return dbManager.getProductsForCategory(categoryName.name());

        } catch (Exception e) {
            System.out.println("Exception was thrown in getProductsForCategory(). Message: " + e.getLocalizedMessage());
            dbManager.dispose();

            return (new RandomStorePopulator()).getProductsForCategory(categoryName);
        }
    }

    private void fillDbByFaker() {

        try {
            RandomStorePopulator randomStorePopulator = new RandomStorePopulator();
            List<Category> categories = randomStorePopulator.getCategories();

            //Fill DB with products for each category using RandomStorePopulator
            for (Category category: categories) {

                //Insert each category into the database
                dbManager.insertCategoryIntoDB(category.name);

                List<Product> products = randomStorePopulator.getProductsForCategory(CategoryEnum.valueOf(category.name));

                //Insert each product into the database
                for (Product p: products) {
                   dbManager.insertProductIntoDB(p.name, category.name, p.price, p.rate);
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception Message in fillDbByFaker(): " + e.getLocalizedMessage());
            dbManager.dispose();
        }
    }
}