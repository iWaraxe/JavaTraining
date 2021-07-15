package by.issoft.store.helpers.populators;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPopulator implements IPopulator {

    public Connection Connection;
    private static final String TableName = "PRODUCT";

    public DBPopulator(Connection connection){
        Connection = connection;
    }

    @Override
    public List<Category> getCategories() {

        if (!doesTableExist()) {

            createTable();
            fillDbByFaker();
        }

        List<Category> categories = new ArrayList<>();

        Statement stmt;
        try {
            Connection.setAutoCommit(false);
            stmt = Connection.createStatement();
            stmt.execute(String.format("SELECT DISTINCT category FROM %s ", TableName));

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                categories.add(new Category(rs.getString("category")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    @Override
    public List<Product> getProductsForCategory(CategoryEnum categoryName) {

        List<Product> productsFromDb = new ArrayList<>();
        Statement stmt;
        try {
            Connection.setAutoCommit(false);
            stmt = Connection.createStatement();
            stmt.execute(String.format("SELECT * FROM %s WHERE category = '%s'", TableName, categoryName.name()));

            ResultSet rs = stmt.getResultSet();

            while(rs.next())
            {
                productsFromDb.add(new Product(rs.getString("name"),rs.getDouble("price"), rs.getDouble("rate")));
            }

            return productsFromDb;

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean doesTableExist() {

        try {
            DatabaseMetaData meta = Connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, TableName, new String[]{"TABLE"});

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void createTable() {

        Statement stmt;
        try {
            Connection.setAutoCommit(false);
            stmt = Connection.createStatement();
            stmt.execute(String.format("CREATE TABLE %s (category VARCHAR(50),name VARCHAR(50),price DECIMAL (4, 2),rate DECIMAL (2, 1))", TableName));

            stmt.close();
            Connection.commit();

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDbByFaker() {

        Statement stmt;
        try {
            Connection.setAutoCommit(false);
            stmt = Connection.createStatement();

            RandomStorePopulator randomStorePopulator = new RandomStorePopulator();
            List<Category> categories = randomStorePopulator.getCategories();

            //Fill DB with products for each category using RandomStorePopulator
            for (Category category: categories) {

                List<Product> products = randomStorePopulator.getProductsForCategory(CategoryEnum.valueOf(category.name));

                //Insert each product into the database
                for (Product p: products) {
                    stmt.execute(String.format("INSERT INTO %s VALUES('%s','%s',%f,%f)", TableName, category.name, p.name, p.price, p.rate));
                }
            }

            stmt.close();
            Connection.commit();

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}