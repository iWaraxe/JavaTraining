package by.issoft.store.helpers;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import by.issoft.store.Store;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.issoft.store.helpers.comparators.ProductComparator;
import by.issoft.store.helpers.comparators.SortOrder;
import by.issoft.store.helpers.comparators.XmlReader;
import by.issoft.store.helpers.populators.IPopulator;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.xml.parsers.ParserConfigurationException;

public class StoreHelper {

    Store store;
    public ExecutorService executorService = Executors.newFixedThreadPool(3);

    public StoreHelper(Store store) {
        this.store = store;
    }

    public void fillStore(IPopulator populator) {

        Map<Category, Integer> categoryProductsMapToAdd = createProductListToAdd();

        for (Map.Entry<Category, Integer> entry : categoryProductsMapToAdd.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {

                Product product = populator.getProductForCategory(CategoryEnum.valueOf(entry.getKey().name));
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

    public List<Product> sortAllProducts() throws Exception {
        Map<String, String> sortBy;
        try {
            XmlReader xml = new XmlReader();
             sortBy = xml.getAllPropertiesToSort();
        }
        catch (ParserConfigurationException e){
            throw new Exception("Error: Config file exception.");
        }
        return sortAllProducts(sortBy);
    }

    public List<Product> sortAllProducts(Map<String, String> sortBy) {

        List<Product> allProducts = this.store.getListOfAllProducts();
        allProducts.sort(new ProductComparator(sortBy));

        return allProducts;
    }

    public List<Product> getTop5() {

        Map<String, String> sortBy = new HashMap<>();
        sortBy.put("price", SortOrder.DESC.toString());

        List<Product> sortedList = sortAllProducts(sortBy);
        List<Product> top5 = new ArrayList<>(sortedList.subList(0, 5));

        return top5;
    }

    public void createOrder(String productName) {

        System.out.println(String.format("%s", "createOrder() is started " + Thread.currentThread().getName()));

        Product orderedProduct = getOrderedProduct(productName);
        int threadTime = new Random().nextInt(30);

        executorService.execute(() -> {
            try {
                System.out.println(String.format("Starting order thread %s", Thread.currentThread().getName()));
                store.purchasedProductList.add(orderedProduct);

                System.out.println(String.format("Actual purchased product list:createOrder "));
                store.printListProducts( store.purchasedProductList);

                System.out.println(String.format("Sleeping for " + threadTime));
                Thread.sleep(threadTime * 1000);

                System.out.println(String.format("Finishing order thread %s", Thread.currentThread().getName()));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(String.format("createOrder() is finished " + Thread.currentThread().getName()));
    }

    public void shutdownThreads(){
        executorService.shutdown();
    }

    private Product getOrderedProduct(String productName)
    {
        Optional<Product> orderedProduct =  store.getListOfAllProducts().stream()
                .filter(x -> x.name.equals(productName))
                .findFirst();

        Product product = orderedProduct.isPresent() ? orderedProduct.get() : null;

        return product;
    }
}
