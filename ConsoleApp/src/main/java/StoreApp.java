import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class StoreApp {

    public static void main(String[] args) {

        Store onlineStore = new Store();

        onlineStore.fillStore(CreateProductListToAdd());
        onlineStore.printAllCategoriesAndProduct();
    }

    private static Map<Category, Integer> CreateProductListToAdd(){
        Map<Category, Integer> productsToAdd = new HashMap<>();

        Reflections reflections = new Reflections(new SubTypesScanner());
        //Get all existed subtypes of Category
        Set<Class<? extends Category>> subTypes = reflections.getSubTypesOf(Category.class);

        //Create a random number of random project for each category
        for (Class<? extends Category> type : subTypes) {
            try {
                Random random = new Random();
                productsToAdd.put(type.getConstructor().newInstance(), random.nextInt(10));

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return productsToAdd;
    }
}
