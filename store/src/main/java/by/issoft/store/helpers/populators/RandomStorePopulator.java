package by.issoft.store.helpers.populators;

import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import by.issoft.domain.products.Book;
import by.issoft.domain.products.Food;
import com.github.javafaker.Faker;

public class RandomStorePopulator implements IPopulator {
    private Faker faker = new Faker();

    @Override
    public Product getProductForCategory(CategoryEnum categoryName){

        switch (categoryName)
        {
            case Food:
                return new Food(faker.food().ingredient(), getPrice(), getRate());
            case Book:
                return new Book(faker.book().title(), getPrice(), getRate());
            default:
                return null;
        }
    }

    private double getPrice() {

        return faker.number().randomDouble(1,1, 100);
    }

    private double getRate(){

        return faker.number().randomDouble(1,0, 5);
    }
}
