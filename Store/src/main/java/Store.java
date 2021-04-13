import java.util.HashMap;
import java.util.Map;

public class Store {

    public Map<Category, Integer> categoryProductsMap = new HashMap<>();

    public Store(){
    }

    public void fillStore(Map<Category, Integer> categoryProductsMap)
    {
        RandomStorePopulator populator = new RandomStorePopulator();

        for (Map.Entry <Category, Integer> entry: categoryProductsMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {

                Product product = new Product(
                        populator.getProductName(entry.getKey().name),
                        populator.getPrice(),
                        populator.getRate());
                entry.getKey().addProduct(product);
            }
        }
        this.categoryProductsMap = categoryProductsMap;
    }

    public void printAllCategoriesAndProduct()
    {
        for (Map.Entry <Category, Integer> entry: categoryProductsMap.entrySet()) {
           entry.getKey().printAllProduct();
        }
    }
}
