package by.issoft.store.helpers.populators;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;

import java.util.List;

public interface IPopulator {

    List<Category> getCategories();

    List<Product> getProductsForCategory(CategoryEnum category);
}
