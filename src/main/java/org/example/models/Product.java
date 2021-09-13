package org.example.models;

public class Product {
    private int id;

    private String name;

    private double price;

    private String status;

    public Product(String name, double price, String status) {
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

    public double getPrice() {
        return price;
    }

    public String getProductStatus() {
        return status;
    }
}
