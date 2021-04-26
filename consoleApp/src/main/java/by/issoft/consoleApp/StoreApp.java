package by.issoft.consoleApp;

import by.issoft.store.Store;
import by.issoft.store.helpers.StoreHelper;

public class StoreApp {

    public static void main(String[] args) {

        Store onlineStore = new Store();
        StoreHelper storeHelper = new StoreHelper(onlineStore);

        storeHelper.fillStoreRandomly();
        onlineStore.printAllCategoriesAndProduct();
    }
}
