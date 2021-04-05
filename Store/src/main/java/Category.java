import java.util.ArrayList;

public class Category{
    public String Name;
    public ArrayList<Product> ProductList;

    public Category(String name) {
        Name = name;
    }

    public void AddProduct(Product product) {
        if(ProductList == null)
        {
            ProductList = new ArrayList<>();
        }
        ProductList.add(product);
    }

    public void PrintAllProduct() {

        final String[][] table = new String[ProductList.toArray().length + 1][];
        table[0] = new String[]{"Name", "Price", "Rate"};

        Integer i = 1;
        for (Product product: ProductList) {

            table[i] = product.GetArrayForPrint();
            i++;
        }

        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
        System.out.println("Category " + Name + " : ");
        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));

        for (final Object[] row : table) {
            System.out.format("%30s%15s%15s%n", row);
        }
    }
}
