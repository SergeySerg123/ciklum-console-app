package org.example.models;

/**
 * Represents product before saving to database
 */
public class Product {
    private int id;

    private String name;

    private int price;

    private String status;

    public Product(String name, int price, String status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getProductStatus() {
        return status;
    }
}
