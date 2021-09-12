package org.example.core;

import org.example.services.ProductService;

import java.util.Scanner;

public class ProductFacade {
    private boolean isValidProductName;
    private boolean isValidProductPrice;
    private final ProductBuilder productBuilder = new ProductBuilder();
    private final ProductService productService = new ProductService();

    public void performCreationProcess() {
        resetValidationStates(); // reset validation states before new loop

        Scanner input = new Scanner(System.in);
        while (!isValidProductName) {
            System.out.println("Enter a new product name, please.");
            String productName = input.nextLine();
            isValidProductName(productName);
        }

        while (!isValidProductPrice) {
            System.out.println("Enter a new product price, please.");
            double productPrice = input.nextDouble();
            isValidProductPrice(productPrice);
        }

        // build product and pass to repository
        productService.createProduct(productBuilder.Build());
    }

    public void provideProducts() {
        String[] productList = productService.getAllProductsAsStringArray();
        System.out.println("| Product ID | Product Name | Product Price | Product Status |");
        for (String productString : productList) {
            System.out.println(productString);
        }
    }

    // TODO: validation
    private void isValidProductName(String productName) {
        isValidProductName = true;
        productBuilder.WithProductName(productName);
    }

    // TODO: validation
    private void isValidProductPrice(double productPrice) {
        isValidProductPrice = true;
        productBuilder.WithProductPrice(productPrice);
    }

    private void resetValidationStates() {
        isValidProductName = false;
        isValidProductPrice = false;
    }
}
