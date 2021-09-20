package org.example.database.interfaces;

import org.example.models.Order;
import org.example.models.viewmodels.OrderViewModel;

import java.sql.*;
import java.util.List;

/**
 * Provides API for communication with database
 */
public interface OrderRepository {

    /**
     * Returns
     * @see OrderViewModel instainces by unique identifier from database
     * @param id represent the order id
     * @return list of OrderViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    List<OrderViewModel> getOrderById(int id) throws SQLException;

    /**
     * Returns all
     * @see OrderViewModel instainces from database
     * @return list of OrderViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    List<OrderViewModel> getAllOrders() throws SQLException;

    /**
     * Creates the new order in database
     * @param order represent
     * @see Order instance
     * @throws SQLException if sql query can't be fulfilled
     */
    void createOrder(Order order) throws SQLException;
}
