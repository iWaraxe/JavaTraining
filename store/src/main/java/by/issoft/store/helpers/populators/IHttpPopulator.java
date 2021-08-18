package by.issoft.store.helpers.populators;

import by.issoft.domain.Product;

import java.util.List;

public interface IHttpPopulator extends IPopulator {

    void addToCart(String productName) throws Exception;
    List<Product> getProductsInCart() throws Exception;
}
