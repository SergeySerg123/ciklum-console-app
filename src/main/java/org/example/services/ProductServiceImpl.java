package org.example.services;

import com.google.inject.Inject;
import org.example.core.logger.Logger;
import org.example.database.interfaces.ProductRepository;
import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.Product;
import org.example.models.viewmodels.ProductViewModel;
import org.example.services.interfaces.ProductService;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of
 * @see ProductService
 */
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Logger logger;

    /**
     * The constructor
     * @param productRepository
     * @param logger
     */
    @Inject
    public ProductServiceImpl(ProductRepository productRepository, Logger logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    /**
     * Returns
     * @see ProductViewModel instaince by unique identifier from database
     * @param id represent the order id
     * @return list of ProductViewModel instances
     */
    @Override
    public ProductViewModel getProductById(int id) {
        ProductViewModel productViewModel = null;

        try {
            productViewModel = productRepository.getProductById(id);
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't retrieve product with id '" + id + "'. SQLException");
        }
        return productViewModel;
    }

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database
     * @return list of OrderedProductViewModel instances
     */
    @Override
    public List<OrderedProductViewModel> getAllOrderedProducts() {
        List<OrderedProductViewModel> productList = null;
        try {
            productList = productRepository.getAllOrderedProducts();
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't retrieve ordered products. SQLException");
        }
        return productList;
    }

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database
     * @return list of OrderedProductViewModel instances
     */
    @Override
    public List<ProductViewModel> getAllProducts() {
        List<ProductViewModel> productList = null;
        try {
            productList = productRepository.getAllProducts();
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't retrieve products. SQLException");
        }
        return productList;
    }

    /**
     * Returns ordered products for future quantity updating in the order list
     * @param orderId represent order unique identifier
     * @return list of OrderedProductViewModel
     */
    @Override
    public List<OrderedProductViewModel> getProductsForQuantityUpdateByOrderId(int orderId) {
        List<OrderedProductViewModel> productViewModelList = null;

        try {
            productViewModelList = productRepository.getProductIdsByOrderId(orderId);
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't retrieve product IDs by order id '" + orderId + "'. SQLException");
        }

        return productViewModelList;
    }

    /**
     * Creates the new product in database
     * @param product represent
     * @see Product instance
     */
    @Override
    public void createProduct(Product product) {
        try {
            productRepository.createProduct(product);
            logger.printSuccessMessage("Product with name '" + product.getName() + "' was successfully created.");
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't create product. SQLException");
        }
    }

    /**
     * Updates product quantity in the order list by order id
     * @param orderId represent order unique identifier
     * @param newQuantity represent new quantity value
     */
    @Override
    public void updateProductQuantityInOrders(int orderId, int newQuantity) {
        try {
            productRepository.updateProductQuantityInOrderList(orderId, newQuantity);
            logger.printSuccessMessage("Product quantity in the order list successfully updated.");
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't update product quantity in the order list. SQLException");
        }
    }

    /**
     * Deletes product by id include related orders
     * @param id represent product unique identifier
     */
    @Override
    public void deleteProductById(int id) {
        try {
            productRepository.deleteProductById(id);
            logger.printSuccessMessage("Product with id '" + id + "' was successfully deleted.");
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't delete product with id '" + id + "'. SQLException");
        }
    }

    /**
     * Deletes all products from database include related orders
     */
    @Override
    public void deleteAllProducts() {
        try {
            productRepository.deleteAllProducts();
            logger.printSuccessMessage("All products with related orders were successfully deleted.");
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't delete all products. SQLException");
        }
    }
}
