package org.example.services;

import org.example.ProductStatus;
import org.example.models.Product;
import org.example.repositories.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository = new ProductRepository();

    public void createProduct(Product product) {
        productRepository.createProduct(product);
    }

    public String[] getAllProductsAsStringArray() {
        List<Product> productList = productRepository.getAllProducts();
        return segmentListToStringArray(productList);
    }

    private String[] segmentListToStringArray(List<Product> productList) {
        String[] productStrings = new String[productList.size()];

        for (int i = 0; i < productStrings.length; i++) {
            Product product = productList.get(i);

            int productId = product.getId();
            String productName = product.getName();
            double productPrice = product.getPrice();
            ProductStatus productStatus = product.getProductStatus();

            productStrings[i] = "   " + productId + "      " + productName + "           " + productPrice + "           " + productStatus.name();
        }

        return productStrings;
    }
}
