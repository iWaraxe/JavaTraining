package by.issoft.consoleApp;

import by.issoft.store.Store;
import by.issoft.store.helpers.DBManager;
import by.issoft.store.helpers.populators.DBPopulator;
import by.issoft.store.helpers.populators.IPopulator;
import by.issoft.store.helpers.populators.RandomStorePopulator;
import by.issoft.store.helpers.StoreHelper;
import by.issoft.store.helpers.TimerCleanupTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;

public class StoreApp {

    public static void main(String[] args) {

        IPopulator populator;
        DBManager dbManager = null;

        try {
            Store onlineStore = new Store();
            StoreHelper storeHelper = new StoreHelper(onlineStore);

            boolean useDb = true;

            if(useDb)
            {
                dbManager = new DBManager();
                populator = new DBPopulator(dbManager);
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

                        if (dbManager != null) {
                            dbManager.dispose();
                        }

                        flag = false;
                        break;
                    default:
                        System.out.println("The command is not recognized.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: the exception was thrown with message:" + e.getLocalizedMessage());

            if (dbManager != null) {
                dbManager.dispose();
            }
        }
    }
}
