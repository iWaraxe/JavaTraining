package by.issoft.store.helpers;

import by.issoft.store.Store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class TimerCleanupTask extends TimerTask {

    Store store;

    public void run() {

        store.purchasedProductList.clear();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Clean up purchased list - " + dtf.format(now));
    }
}
