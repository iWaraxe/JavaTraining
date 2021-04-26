package by.issoft.store.helpers;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.store.Store;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class StoreHelper {

    Store store;

    public StoreHelper(Store store) {
        this.store = store;
    }

    public void fillStoreRandomly() {

        RandomStorePopulator populator = new RandomStorePopulator();
        Map<Category, Integer> categoryProductsMapToAdd = createProductListToAdd();

        for (Map.Entry<Category, Integer> entry : categoryProductsMapToAdd.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {

                Product product = new Product(
                        populator.getProductName(entry.getKey().name),
                        populator.getPrice(),
                        populator.getRate());
                entry.getKey().addProduct(product);
            }

            this.store.categoryList.add(entry.getKey());
        }
    }

    private static Map<Category, Integer> createProductListToAdd() {
        Map<Category, Integer> productsToAdd = new HashMap<>();

        Reflections reflections = new Reflections("by.issoft.domain.categories", new SubTypesScanner());
        //Get all existed subtypes of Category
        Set<Class<? extends Category>> subTypes = reflections.getSubTypesOf(Category.class);

        //Create a random number of random products for each category
        for (Class<? extends Category> type : subTypes) {
            try {
                Random random = new Random();
                productsToAdd.put(type.getConstructor().newInstance(), random.nextInt(10));

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return productsToAdd;
    }
}
