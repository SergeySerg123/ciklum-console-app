package org.example.services.interfaces;

import org.example.models.Order;
import org.example.models.viewmodels.OrderViewModel;

import java.sql.SQLException;
import java.util.List;

/**
 * The order service. Contains CRUD operation for communication with data layer and exception handles
 */
public interface OrderService {

    /**
     * Creates the new order in database
     * @param order represent
     * @see Order instance
     */
    void createOrder(Order order);

    /**
     * Returns all
     * @see OrderViewModel instainces from database
     * @return list of OrderViewModel instances
     */
    List<OrderViewModel> getOrders();

    /**
     * Returns
     * @see OrderViewModel instainces by unique identifier from database
     * @param orderId represent the order id
     * @return list of OrderViewModel instances
     */
    List<OrderViewModel> getOrderById(int orderId);
}
