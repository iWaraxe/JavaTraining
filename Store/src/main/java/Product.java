public class Product {
    public String Name;
    public Double Price;
    public Double Rate;

    public Product(String name, Double price, Double rate){
        Name = name;
        Price = price;
        Rate = rate;
    }

    public String[] GetArrayForPrint(){
       return new String[] { this.Name, this.Price.toString(), this.Rate.toString()};
    }
}