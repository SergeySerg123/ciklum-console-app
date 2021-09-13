package org.example.core;

import org.example.models.Product;

public class ProductBuilder {

    private String productName;
    private double productPrice;
    private String productStatus;

    public void WithProductName(String name) {
        productName = name;
    }

    public void WithProductPrice(double price) {
        productPrice = price;
    }

    public void WithProductStatus(String status) {
        productStatus = status;
    }

    public Product Build() {
        return new Product(productName, productPrice, productStatus);
    }
}
