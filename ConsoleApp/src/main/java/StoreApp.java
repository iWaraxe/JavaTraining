import java.util.HashMap;
import java.util.Map;

public class StoreApp {
    public static void main(String[] args){

        Store onlineStore = new Store();

        Map<Category, Integer> productsToAdd= new HashMap<>();
        productsToAdd.put(new FoodCategory(), 5);
        productsToAdd.put(new BookCategory(), 6);

        onlineStore.FillStore(productsToAdd);
        onlineStore.PrintAllCategoriesAndProduct();
    }
}
