package org.example.core;

import org.example.ProductStatus;
import org.example.services.ProductService;

import java.util.Scanner;

public class ProductFacade {
    private boolean isValidProductName;
    private boolean isValidProductPrice;
    private boolean isValidProductStatus;
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

        while (!isValidProductStatus) {
            System.out.println("Enter a new product status, please.");
            System.out.println("0 - in stock.");
            System.out.println("1 - out of stock.");
            System.out.println("2 - running low.");

            int productPrice = input.nextInt();
            isValidProductStatus(productPrice);
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

    private void isValidProductStatus(int statusNumber) {
        switch (statusNumber) {
            case 0 -> {
                productBuilder.WithProductStatus(ProductStatus.in_stock.name());
                isValidProductStatus = true;
            }
            case 1 -> {
                productBuilder.WithProductStatus(ProductStatus.out_of_stock.name());
                isValidProductStatus = true;
            }
            case 2 -> {
                productBuilder.WithProductStatus(ProductStatus.running_low.name());
                isValidProductStatus = true;
            }
            default -> System.out.println("Entered invalid value. Please, try again.");
        }
    }

    private void resetValidationStates() {
        isValidProductName = false;
        isValidProductPrice = false;
        isValidProductStatus = false;
    }
}
