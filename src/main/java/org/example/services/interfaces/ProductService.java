package org.example.services.interfaces;

import org.example.models.Product;
import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.viewmodels.ProductViewModel;

import java.util.List;

/**
 * The order service. Contains CRUD operation for communication with data layer and exception handles
 */
public interface ProductService {

    /**
     * Returns
     * @see ProductViewModel instaince by unique identifier from database
     * @param id represent the order id
     * @return list of ProductViewModel instances
     */
    ProductViewModel getProductById(int id);

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database
     * @return list of OrderedProductViewModel instances
     */
    List<OrderedProductViewModel> getAllOrderedProducts();

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database
     * @return list of OrderedProductViewModel instances
     */
    List<ProductViewModel> getAllProducts();

    /**
     * Returns ordered products for future quantity updating in the order list
     * @param orderId represent order unique identifier
     * @return list of OrderedProductViewModel
     */
    List<OrderedProductViewModel> getProductsForQuantityUpdateByOrderId(int orderId);

    /**
     * Creates the new product in database
     * @param product represent
     * @see Product instance
     */
    void createProduct(Product product);

    /**
     * Updates product quantity in the order list by order id
     * @param orderId represent order unique identifier
     * @param newQuantity represent new quantity value
     */
    void updateProductQuantityInOrders(int orderId, int newQuantity);

    /**
     * Deletes product by id include related orders
     * @param id represent product unique identifier
     */
    void deleteProductById(int id);

    /**
     * Deletes all products from database include related orders
     */
    void deleteAllProducts();
}
