package by.issoft.consoleApp;

import by.issoft.store.Store;
import by.issoft.store.helpers.StoreHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StoreApp {

    public static void main(String[] args) {
        try {
            Store onlineStore = new Store();
            StoreHelper storeHelper = new StoreHelper(onlineStore);

            storeHelper.fillStoreRandomly();
            onlineStore.printAllCategoriesAndProduct();

            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Boolean flag = true;
            while (flag) {

                System.out.println("Enter command sort/top/quit:");
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
                    case "quit":
                        flag = false;
                        break;
                    default:
                        System.out.println("The command is not recognized.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: the exception was thrown with message:" + e.getMessage());
        }
    }
}
