package by.issoft.store.helpers.populators;

import by.issoft.domain.categories.CategoryName;

public interface IPopulator {

    public String getName(CategoryName categoryName);

    public double getPrice();

    public double getRate();
}
