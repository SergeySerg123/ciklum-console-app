package org.example.core.facades;

import com.google.inject.Inject;
import org.example.enums.ProductStatus;
import org.example.core.builders.ProductBuilder;
import org.example.core.checker.Checker;
import org.example.core.logger.Logger;
import org.example.core.scanner.ScannerProvider;
import org.example.helpers.ProductSegmentationHelper;
import org.example.models.viewmodels.ProductViewModel;
import org.example.services.interfaces.ProductService;

import java.util.Scanner;

/**
 * Implementation of
 * @see ProductFacade
 */
public class ProductFacadeImpl implements ProductFacade {
    private boolean isValidProductStatus;
    private boolean isValidDeletionProductId;
    private boolean isMatchPasswords;

    private final ProductBuilder productBuilder = new ProductBuilder();

    private final Logger logger;
    private final ProductService productService;
    private final Checker checker;
    private final ScannerProvider scannerProvider;

    /**
     * The constructor
     * @param logger
     * @param productService
     * @param checker
     * @param scannerProvider
     */
    @Inject
    public ProductFacadeImpl(Logger logger, ProductService productService, Checker checker, ScannerProvider scannerProvider) {
        this.logger = logger;
        this.productService = productService;
        this.checker = checker;
        this.scannerProvider = scannerProvider;
    }

    /**
     * Provides product creation
     */
    @Override
    public void provideProductCreation() {
        resetValidationStates();

        Scanner input = scannerProvider.provideScanner();

        logger.printInfoMessage("Enter a new product name, please.");
        input.nextLine(); // move to next line
        String productName = input.nextLine();
        productBuilder.WithProductName(productName);

        logger.printInfoMessage("Enter a new product price, please.");
        int productPrice = input.nextInt();
        productBuilder.WithProductPrice(productPrice);

        while (!isValidProductStatus) {
            logger.printInfoMessage("Enter a new product status, please.");
            logger.printInfoMessage("0 - in stock.");
            logger.printInfoMessage("1 - out of stock.");
            logger.printInfoMessage("2 - running low.");

            int productStatus = input.nextInt();
            isValidProductStatus(productStatus);
        }

        // build product and pass to repository
        productService.createProduct(productBuilder.Build());

        input.nextLine(); // move to next line
    }

    /**
     * Provides all products
     */
    @Override
    public void provideProducts() {
        String[] productList = ProductSegmentationHelper.segmentProductListToStringArray(productService.getAllProducts());

        if(productList.length == 0) {
            logger.printWarningMessage("No products.");
            return;
        }

        logger.printInfoMessage("| Product ID | Product Name | Product Price | Product Status |");
        for (String productString : productList) {
            logger.printInfoMessage(productString);
        }
    }

    /**
     * Provides all ordered products
     */
    @Override
    public void provideOrderedProducts() {
        String[] productList = ProductSegmentationHelper.segmentOrderedProductListToStringArray(productService.getAllOrderedProducts());

        if(productList.length == 0) {
            logger.printWarningMessage("No ordered products.");
            return;
        }

        logger.printInfoMessage("| Product ID | Product Name | Product quantity |");
        for (String productString : productList) {
            logger.printInfoMessage(productString);
        }
    }

    /**
     * Provides product deletion
     */
    @Override
    public void provideProductDeletion() {
        resetValidationStates();

        int deletionProductId = 0;

        Scanner input = scannerProvider.provideScanner();

        while (!isValidDeletionProductId) {
            logger.printInfoMessage("Enter the deletion product id, please.");
            deletionProductId = input.nextInt();
            isValidProductDeletionId(deletionProductId);
        }

        input.nextLine(); // move to next line

        while (!isMatchPasswords) {
            logger.printInfoMessage("Enter the password for deletion product with id: " + deletionProductId);
            String password = input.nextLine();
            isMatchPasswords = checker.isMatch(password);
            if (!isMatchPasswords) {
                logger.printDangerMessage("Entered invalid password. Try again.");
            }
        }

        productService.deleteProductById(deletionProductId);
    }

    /**
     * Provides all products deletion
     */
    @Override
    public void provideAllProductDeletion() {
        resetValidationStates();

        Scanner input = scannerProvider.provideScanner();

        input.nextLine(); // move to next line

        while (!isMatchPasswords) {
            logger.printInfoMessage("Enter the password for deletion all products in the database.");
            String password = input.nextLine();
            isMatchPasswords = checker.isMatch(password);
            if (!isMatchPasswords) {
                logger.printDangerMessage("Entered invalid password. Try again.");
            }
        }

        productService.deleteAllProducts();
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
            default -> logger.printDangerMessage("Entered invalid value. Please, try again.");
        }
    }

    private void isValidProductDeletionId(int deletionProductId) {
        ProductViewModel productForDeletion = productService.getProductById(deletionProductId);
        if (productForDeletion == null) {
            logger.printWarningMessage("Product with id '" + deletionProductId + "' not found.");
            return;
        }
        isValidDeletionProductId = true;
    }

    private void resetValidationStates() {
        isValidProductStatus = false;
        isValidDeletionProductId = false;
        isMatchPasswords = false;
    }
}
