package by.issoft.store.helpers.populators;

import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;

public interface IPopulator {

    public Product getProductForCategory(CategoryEnum categoryName);
}
