package by.issoft.store.helpers;

import by.issoft.domain.Category;
import by.issoft.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager implements IDBManager {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/OnlineStore";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    public static final String TABLE_CATEGORY_NAME = "CATEGORY";
    public static final String TABLE_PRODUCT_NAME = "PRODUCT";

    public static Connection  connection = null;

    public DBManager()
    {
        if(connection == null) {
            connection = getDBConnection();
        }
    }

    @Override
    public boolean createTableIfDoesNotExist() throws Exception {
        boolean isTableJustCreated = false;

        if (!doesTableExist(TABLE_CATEGORY_NAME)) {

            createCategoryTable();
            isTableJustCreated = true;
        }

        if (!doesTableExist(TABLE_PRODUCT_NAME)) {

            createProductTable();
            isTableJustCreated = true;
        }

        return isTableJustCreated;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

        Statement stmt;
        stmt = connection.createStatement();
        stmt.execute(String.format("SELECT * FROM %s ", TABLE_CATEGORY_NAME));

        ResultSet rs = stmt.getResultSet();

        while (rs.next()) {
            categories.add(new Category(rs.getString("category")));
        }

        return categories;
    }

    @Override
    public List<Product> getProductsForCategory(String categoryName) throws SQLException {
        List<Product> productsFromDb = new ArrayList<>();

        Statement stmt;
        stmt = connection.createStatement();

        String getCategoryIdCommand = String.format("SELECT id FROM %s WHERE category='%s'", TABLE_CATEGORY_NAME, categoryName);
        stmt.execute(String.format("SELECT * FROM %s WHERE category_id= %s", TABLE_PRODUCT_NAME, getCategoryIdCommand));

        ResultSet rs = stmt.getResultSet();

        while (rs.next()) {
            productsFromDb.add(new Product(rs.getString("name"), rs.getDouble("price"), rs.getDouble("rate")));
        }

        return productsFromDb;
    }

    @Override
    public void insertCategoryIntoDB(String categoryName) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();

        stmt.execute(String.format("INSERT INTO %s(category) VALUES('%s')", TABLE_CATEGORY_NAME, categoryName));
    }

    @Override
    public void insertProductIntoDB(String name, String category, double price, double rate) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();

        String getCategoryIdCommand = String.format("SELECT id FROM %s WHERE category='%s'", TABLE_CATEGORY_NAME, category);
        stmt.execute(String.format("INSERT INTO %s(name,category_id,price,rate) VALUES('%s',%s,%f,%f)",
                TABLE_PRODUCT_NAME,  name, getCategoryIdCommand, price, rate));

    }

    private static boolean doesTableExist(String tableName) throws Exception {

        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }

    private static void createProductTable() throws SQLException {

        Statement stmt;
        stmt = connection.createStatement();
        stmt.execute(String.format("CREATE TABLE %s (id INTEGER IDENTITY PRIMARY KEY, name VARCHAR(50) UNIQUE, category_id INTEGER NOT NULL, price DECIMAL (4, 2), rate DECIMAL (2, 1)," +
                "FOREIGN KEY (category_id) REFERENCES %s (id) ON DELETE CASCADE ON UPDATE CASCADE)", TABLE_PRODUCT_NAME, TABLE_CATEGORY_NAME));

        stmt.close();
        connection.commit();
    }

    private static void createCategoryTable() throws SQLException {

        Statement stmt;
        stmt = connection.createStatement();
        stmt.execute(String.format("CREATE TABLE %s (id INTEGER IDENTITY PRIMARY KEY, category VARCHAR(50) NOT NULL)", TABLE_CATEGORY_NAME));

        stmt.close();
        connection.commit();
    }

    @Override
    public Connection getDBConnection() {

        try {
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            System.out.println("DB Driver  was not found. Exception message: " + e.getLocalizedMessage());
        }

        try {
            return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

        } catch (SQLException e) {
            System.out.println("Exception when getting database connection. Exception message: " + e.getLocalizedMessage());
        }

        return null;
    }

    private void closeDBConnection() {

        try {
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            System.out.println("Exception was thrown when closing database connection: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {

        closeDBConnection();
        connection = null;
    }
}
