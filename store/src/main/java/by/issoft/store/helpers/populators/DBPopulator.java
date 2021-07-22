package by.issoft.store.helpers.populators;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import by.issoft.store.helpers.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPopulator implements IPopulator {

    public DBManager dbManager;

    public DBPopulator(DBManager dbManager){

        this.dbManager = dbManager;
    }

    @Override
    public List<Category> getCategories() {

        List<Category> categories = new ArrayList<>();

        try {

            if (dbManager.createTableIfDoesNotExist()) {
                fillDbByFaker();
            }

            Statement stmt;
            stmt = dbManager.connection.createStatement();
            stmt.execute(String.format("SELECT * FROM %s ", dbManager.TABLE_CATEGORY_NAME));

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                categories.add(new Category(rs.getString("category")));
            }

            return categories;

        } catch (Exception ex) {
            System.out.println("Exception was thrown in getCategories(). Message: " + ex.getLocalizedMessage());
            dbManager.dispose();

            categories = (new RandomStorePopulator()).getCategories();
            return categories;
        }
    }

    @Override
    public List<Product> getProductsForCategory(CategoryEnum categoryName) {

        List<Product> productsFromDb = new ArrayList<>();
        Statement stmt;
        try {
            stmt = dbManager.connection.createStatement();
            stmt.execute(String.format("SELECT * FROM %s WHERE category = '%s'", dbManager.TABLE_PRODUCT_NAME, categoryName.name()));

            ResultSet rs = stmt.getResultSet();

            while(rs.next())
            {
                productsFromDb.add(new Product(rs.getString("name"),rs.getDouble("price"), rs.getDouble("rate")));
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("Exception was thrown in getProductsForCategory(). Message: " + e.getLocalizedMessage());
            dbManager.dispose();

            productsFromDb = (new RandomStorePopulator()).getProductsForCategory(categoryName);
            return productsFromDb;
        }

        return productsFromDb;
    }

    private void fillDbByFaker() {

        Statement stmt;
        try {
            stmt = dbManager.connection.createStatement();

            RandomStorePopulator randomStorePopulator = new RandomStorePopulator();
            List<Category> categories = randomStorePopulator.getCategories();

            //Fill DB with products for each category using RandomStorePopulator
            for (Category category: categories) {

                //Insert each category into the database
                stmt.execute(String.format("INSERT INTO %s(category) VALUES('%s')", dbManager.TABLE_CATEGORY_NAME, category.name));

                List<Product> products = randomStorePopulator.getProductsForCategory(CategoryEnum.valueOf(category.name));

                //Insert each product into the database
                for (Product p: products) {
                    stmt.execute(String.format("INSERT INTO %s(name,category,price,rate) VALUES('%s','%s',%f,%f)", dbManager.TABLE_PRODUCT_NAME, p.name, category.name, p.price, p.rate));
                }
            }

            stmt.close();
            dbManager.connection.commit();

        } catch (SQLException e) {
            System.out.println("SQL Exception Message in fillDbByFaker(): " + e.getLocalizedMessage());
            dbManager.dispose();
        }
    }
}