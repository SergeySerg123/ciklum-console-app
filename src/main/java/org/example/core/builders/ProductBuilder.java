package org.example.core.builders;

import org.example.models.Product;

/**
 * Provides mechanism for building
 * @see Product
 */
public class ProductBuilder {

    private String productName;
    private int productPrice;
    private String productStatus;

    public void WithProductName(String name) {
        productName = name;
    }

    public void WithProductPrice(int price) {
        productPrice = price;
    }

    public void WithProductStatus(String status) {
        productStatus = status;
    }

    public Product Build() {
        return new Product(productName, productPrice, productStatus);
    }
}
