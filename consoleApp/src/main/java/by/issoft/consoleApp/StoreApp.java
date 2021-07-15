package by.issoft.consoleApp;

import by.issoft.store.Store;
import by.issoft.store.helpers.populators.DBPopulator;
import by.issoft.store.helpers.populators.IPopulator;
import by.issoft.store.helpers.populators.RandomStorePopulator;
import by.issoft.store.helpers.StoreHelper;
import by.issoft.store.helpers.TimerCleanupTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;

public class StoreApp {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/OnlineStore";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {

        Connection connection = null;
        IPopulator populator;

        try {
            Store onlineStore = new Store();
            StoreHelper storeHelper = new StoreHelper(onlineStore);

            boolean useDb = true;

            if(useDb)
            {
                connection = getDBConnection();
                populator = new DBPopulator(connection);
            }
            else {
                populator = new RandomStorePopulator();
            }

            storeHelper.fillStore(populator);
            onlineStore.printAllCategoriesAndProduct();

            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //Cleanup purchased product list products every 30 seconds
            Timer timer = new Timer();
            timer.schedule(new TimerCleanupTask(), 0,60000);

            boolean flag = true;
            while (flag) {

                System.out.println("Enter command sort/top/createOrder/quit:");
                String command = reader.readLine();

                System.out.println("Your command is : " + command);
                switch (command) {
                    case "sort":
                        onlineStore.printListProducts(storeHelper.sortAllProducts());
                        break;
                    case "top":
                        System.out.println("Print top 5 products sorted via price desc.");
                        onlineStore.printListProducts(storeHelper.getTop5());
                        break;
                    case "createOrder":
                        System.out.println("Enter name of product to order:");
                        String productName = reader.readLine();

                        storeHelper.createOrder(productName);
                        break;
                    case "quit":
                        timer.cancel();
                        storeHelper.shutdownThreads();

                        flag = false;
                        break;
                    default:
                        System.out.println("The command is not recognized.");
                }

                if(useDb){

                    closeDBConnection(connection);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: the exception was thrown with message:" + e.getMessage());
        }
    }

    private static Connection getDBConnection() {

        Connection dbConnection;

        try {
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static void closeDBConnection(Connection connection) {

        try {
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
