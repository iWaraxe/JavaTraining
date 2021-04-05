import java.util.HashMap;
import java.util.Map;

public class Store {

    public Map<Category, Integer> CategoryProductsMap = new HashMap<>();

    public Store()
    {
    }

    public void FillStore(Map<Category, Integer> categoryProductsMap)
    {
        RandomStorePopulator populator = new RandomStorePopulator();

        for (Map.Entry <Category, Integer> entry: categoryProductsMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {

                Product product = new Product(
                        populator.GetProductName(entry.getKey().Name),
                        populator.GetPrice(),
                        populator.GetRate());
                entry.getKey().AddProduct(product);
            }
        }
        CategoryProductsMap = categoryProductsMap;
    }

    public void PrintAllCategoriesAndProduct()
    {
        for (Map.Entry <Category, Integer> entry: CategoryProductsMap.entrySet()) {
           entry.getKey().PrintAllProduct();
        }
    }
}
