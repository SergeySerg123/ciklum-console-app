package org.example.database.interfaces;

import org.example.models.Product;
import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.viewmodels.ProductViewModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides API for communication with database
 */
public interface ProductRepository {

    /**
     * Returns
     * @see ProductViewModel instaince by unique identifier from database
     * @param id represent the order id
     * @return list of ProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    ProductViewModel getProductById(int id) throws SQLException;

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database
     * @return list of OrderedProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    List<OrderedProductViewModel> getAllOrderedProducts() throws SQLException;

    /**
     * Returns all
     * @see ProductViewModel instainces from database
     * @return list of ProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    List<ProductViewModel> getAllProducts() throws SQLException;

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database by unique identifier
     * @return list of OrderedProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    List<OrderedProductViewModel> getProductIdsByOrderId(int orderId) throws SQLException;

    /**
     * Creates the new product in database
     * @param product represent
     * @see Product instance
     * @throws SQLException if sql query can't be fulfilled
     */
    void createProduct(Product product) throws SQLException;

    /**
     * Updates product quantity in the order list by order id
     * @param orderId represent order unique identifier
     * @param newQuantity represent new quantity value
     * @throws SQLException if sql query can't be fulfilled
     */
    void updateProductQuantityInOrderList(int orderId, int newQuantity) throws SQLException;

    /**
     * Deletes product by id include related orders
     * @param id represent product unique identifier
     * @throws SQLException if sql query can't be fulfilled
     */
    void deleteProductById(int id) throws SQLException;

    /**
     * Deletes all products from database include related orders
     * @throws SQLException if sql query can't be fulfilled
     */
    void deleteAllProducts() throws SQLException;
}
