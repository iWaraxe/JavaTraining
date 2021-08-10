package by.issoft.store.helpers.populators;

import by.issoft.domain.Product;

import java.util.List;

public interface IHttpPopulator extends IPopulator {

    void addToCart(String productName);
    List<Product> getProductsInCart();
}
