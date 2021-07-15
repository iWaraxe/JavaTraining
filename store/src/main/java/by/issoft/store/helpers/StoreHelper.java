package by.issoft.store.helpers;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import by.issoft.store.Store;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.issoft.store.helpers.comparators.ProductComparator;
import by.issoft.store.helpers.comparators.SortOrder;
import by.issoft.store.helpers.comparators.XmlReader;
import by.issoft.store.helpers.populators.IPopulator;

import javax.xml.parsers.ParserConfigurationException;

public class StoreHelper {

    Store store;
    public ExecutorService executorService = Executors.newFixedThreadPool(3);

    public StoreHelper(Store store) {
        this.store = store;
    }

    public void fillStore(IPopulator populator) {

        List<Category> categories = populator.getCategories();
        this.store.categoryList.addAll(categories);

        //fill store with products for each category
        for (Category category : categories) {

            List<Product> products = populator.getProductsForCategory(CategoryEnum.valueOf(category.name));
            category.addProducts(products);
        }
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

        System.out.printf("%s%n", "createOrder() is started " + Thread.currentThread().getName());

        Product orderedProduct = getOrderedProduct(productName);
        int threadTime = new Random().nextInt(30);

        executorService.execute(() -> {
            try {
                System.out.printf("Starting order thread %s%n", Thread.currentThread().getName());
                store.purchasedProductList.add(orderedProduct);

                System.out.println("Actual purchased product list:createOrder ");
                store.printListProducts( store.purchasedProductList);

                System.out.println("Sleeping for " + threadTime);
                Thread.sleep(threadTime * 1000);

                System.out.printf("Finishing order thread %s%n", Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("createOrder() is finished " + Thread.currentThread().getName());
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
