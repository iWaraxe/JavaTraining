package by.issoft.store.helpers.populators;

import by.issoft.domain.categories.CategoryName;
import com.github.javafaker.Faker;

public class RandomStorePopulator implements IPopulator {
    private Faker faker = new Faker();

    @Override
    public String getName(CategoryName categoryName){

        switch (categoryName)
        {
            case Food:
                return faker.food().ingredient();
            case Book:
                return faker.book().title();
            default:
                return null;
        }
    }

    @Override
    public double getPrice() {

        return faker.number().randomDouble(1,1, 100);
    }

    @Override
    public double getRate(){

        return faker.number().randomDouble(1,0, 5);
    }
}
