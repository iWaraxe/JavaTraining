package by.issoft.store.httpServer;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import by.issoft.store.helpers.populators.DBPopulator;
import by.issoft.store.helpers.populators.IPopulator;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StoreHttpServer {

    public static final int PORT = 8080;
    public static final String BASE_URL = String.format("http://localhost:%s", PORT);
    public static final String GET_CATEGORIES_URL = String.format("%s/categories", BASE_URL);
    public static final String GET_PRODUCTS_FOR_CATEGORY_URL = String.format("%s/productsForCategory", BASE_URL);
    public static final String CART_URL = String.format("%s/cart", BASE_URL);

    public IPopulator populator = new DBPopulator();
    public List<Category> categoryList = new ArrayList<>();
    public static List<Product> cartProductList = new ArrayList<>();

    public StoreHttpServer()
    {
        run();
    }

    public void run() {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

            List<Category> allCategories = (new DBPopulator()).getCategories();
            for (Category category:  allCategories )
            {
                server.createContext(String.format("/productsForCategory/%s", category.name), new GetProductsForCategoryHandler(CategoryEnum.valueOf(category.name), this));
            }
            server.createContext("/categories", new GetCategoriesHandler(this));
            server.createContext("/cart", new GetCartHandler(this));

            System.out.println("server started at " + PORT);
            server.setExecutor(null);
            server.start();

        } catch (IOException e) {
            System.out.println("Error: Can't create a server on port" + PORT);
        }
    }

    public List<Category> getCategories() {

        if (categoryList != null) {
            categoryList = populator.getCategories();
        }

        return categoryList;
    }

    public List<Product> getProductsForCategory(CategoryEnum categoryName) {

        List<Product> products = populator.getProductsForCategory(categoryName);

        //add list of products to appropriate category
        Category category = categoryList.stream().filter(x -> x.name.equals(categoryName.name())).findFirst().get();
        category.addProducts(products);

        return products;
    }

    public List<Product> getProductsInCart() {

        return cartProductList;
    }

    public Product addProductToCart(String productName) {

        Product product = getSelectedProduct(productName);
        cartProductList.add(product);

        return product;
    }

    private Product getSelectedProduct(String productName)
    {
        Optional<Product> orderedProduct =  getAllProducts().stream()
                .filter(x -> x.name.equals(productName))
                .findFirst();

        Product product = orderedProduct.isPresent() ? orderedProduct.get() : null;

        return product;
    }

    private List<Product> getAllProducts() {

        List<Product> allProducts = new ArrayList<>();

        for (Category category : this.categoryList) {
            allProducts.addAll(category.productList);
        }

        return allProducts;
    }
}

