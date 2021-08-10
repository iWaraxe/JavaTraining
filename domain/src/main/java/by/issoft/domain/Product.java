package by.issoft.domain;

public class Product {
    public String name;
    public double price;
    public double rate;

    public Product(String name, double price, double rate) {

        this.name = name;
        this.price = price;
        this.rate = rate;
    }

    @Override
    public String toString() {

        return String.format("Name: '%s', Price: %.2f, Rate: %.1f", name, price, rate);
    }
}