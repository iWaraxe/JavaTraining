package by.issoft.store.helpers;

import java.sql.*;

public class DBManager {

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

    public static boolean createTableIfDoesNotExist() throws Exception {
        boolean isTableJustCreated = false;

        if (!doesTableExist(TABLE_PRODUCT_NAME)) {

            createProductTable();
            isTableJustCreated = true;
        }

        if (!doesTableExist(TABLE_CATEGORY_NAME)) {

            createCategoryTable();
            isTableJustCreated = true;
        }

        return isTableJustCreated;
    }

    private static boolean doesTableExist(String tableName) throws Exception {

        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }

    private static void createProductTable() throws SQLException {

        Statement stmt;
        stmt = connection.createStatement();
        stmt.execute(String.format("CREATE TABLE %s (id INTEGER IDENTITY PRIMARY KEY, name VARCHAR(50) UNIQUE, category VARCHAR(50), price DECIMAL (4, 2), rate DECIMAL (2, 1))", TABLE_PRODUCT_NAME));

        stmt.close();
        connection.commit();
    }

    private static void createCategoryTable() throws SQLException {

        Statement stmt;
        stmt = connection.createStatement();
        stmt.execute(String.format("CREATE TABLE %s (id INTEGER IDENTITY PRIMARY KEY, category VARCHAR(50))", TABLE_CATEGORY_NAME));

        stmt.close();
        connection.commit();
    }

    private static Connection getDBConnection() {

        try {
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            System.out.println("DB Driver  was not found. Exception message: " + e.getLocalizedMessage());
            return null;
        }

        try {
            return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

        } catch (SQLException e) {
            System.out.println("Exception when getting database connection. Exception message: " + e.getLocalizedMessage());
            return null;
        }
    }

    private static void closeDBConnection() {

        try {
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            System.out.println("Exception was thrown when closing database connection: " + e.getMessage());
        }
    }

    public static void dispose() {

        closeDBConnection();
        connection = null;
    }
}
