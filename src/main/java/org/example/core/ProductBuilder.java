package org.example.core;

import org.example.models.Product;

public class ProductBuilder {

    private String productName;
    private double productPrice;

    public void WithProductName(String name) {
        productName = name;
    }

    public void WithProductPrice(double price) {
        productPrice = price;
    }

    public Product Build() {
        return new Product(productName, productPrice);
    }
}
