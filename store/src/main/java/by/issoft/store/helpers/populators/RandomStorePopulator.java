package by.issoft.store.helpers.populators;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import com.github.javafaker.Faker;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomStorePopulator implements IPopulator {
    private Faker faker = new Faker();

    @Override
    public List<Category> getCategories() {

        List<Category>  categories = new ArrayList<>();

        Reflections reflections = new Reflections("by.issoft.domain.categories", new SubTypesScanner());
        //Get all existed subtypes of Category
        Set<Class<? extends Category>> subTypes = reflections.getSubTypesOf(Category.class);

        //add all existing categories to the list
        for (Class<? extends Category> type : subTypes) {
            try {
                categories.add(type.getConstructor().newInstance());

            } catch (Exception e) {
                System.out.println("Exception was thrown with message:" + e.getLocalizedMessage());
            }
        }

        return categories;
    }

    @Override
    public List<Product> getProductsForCategory(CategoryEnum category){

        List<Product> resultList = new ArrayList<>();
        Random random = new Random();
        int productCount = random.nextInt(10);

        resultList.addAll(generateProductList(category, productCount));

        return resultList;
    }

    private List<Product> generateProductList(CategoryEnum category, int count) {
        List<Product> resultList = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            resultList.add(new Product(generateFakeProductName(category), getPrice(), getRate()));
        }

        return resultList;
    }

    private String generateFakeProductName(CategoryEnum category) {

        switch (category) {
            case Food:
                return faker.food().ingredient();
            case Book:
                return faker.book().title();
            default:
                return null;
        }
    }

    private double getPrice() {

        return faker.number().randomDouble(1, 1, 100);
    }

    private double getRate() {

        return faker.number().randomDouble(1, 0, 5);
    }
}
