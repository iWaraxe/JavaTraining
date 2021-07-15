package by.issoft.store.helpers.populators;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categories.CategoryEnum;
import com.github.javafaker.Faker;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
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

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return categories;
    }

    @Override
    public List<Product> getProductsForCategory(CategoryEnum category){

        List<Product> resultList = new ArrayList<>();
        Random random = new Random();
        int productCount = random.nextInt(10);

        switch (category)
        {
            case Food:
                for (int i = 0; i < productCount; i++) {

                    resultList.add(new Product(faker.food().ingredient(), getPrice(), getRate()));
                }
                break;
            case Book:
                for (int i = 0; i < productCount; i++) {

                    resultList.add(new Product(faker.book().title(), getPrice(), getRate()));
                }
                break;
            default:
                return null;
        }

        return resultList;
    }

    private double getPrice() {

        return faker.number().randomDouble(1,1, 100);
    }

    private double getRate(){

        return faker.number().randomDouble(1,0, 5);
    }
}
