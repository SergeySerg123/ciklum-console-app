package org.example.services;

import com.google.inject.Inject;
import org.example.core.logger.Logger;
import org.example.database.interfaces.OrderRepository;
import org.example.models.Order;
import org.example.models.viewmodels.OrderViewModel;
import org.example.services.interfaces.OrderService;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of
 * @see OrderService
 */
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final Logger logger;

    /**
     * The constructor
     * @param orderRepository
     * @param logger
     */
    @Inject
    public OrderServiceImpl(OrderRepository orderRepository, Logger logger) {
        this.orderRepository = orderRepository;
        this.logger = logger;
    }

    /**
     * Creates the new order in database
     * @param order represent
     * @see Order instance
     */
    @Override
    public void createOrder(Order order) {
        try {
            orderRepository.createOrder(order);
            logger.printSuccessMessage("Order with status '" + order.getStatus() + "' was successfully created.");
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't create order. SQLException");
        }
    }

    /**
     * Returns all
     * @see OrderViewModel instainces from database
     * @return list of OrderViewModel instances
     */
    @Override
    public List<OrderViewModel> getOrders() {
        List<OrderViewModel> orders = null;
        try {
            orders = orderRepository.getAllOrders();
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't retrieve orders. SQLException");
        }
        return orders;
    }

    /**
     * Returns
     * @see OrderViewModel instainces by unique identifier from database
     * @param orderId represent the order id
     * @return list of OrderViewModel instances
     */
    @Override
    public List<OrderViewModel> getOrderById(int orderId) {
        List<OrderViewModel> orders = null;
        try {
            orders = orderRepository.getOrderById(orderId);
        } catch (SQLException e) {
            logger.printDangerMessage("Couldn't retrieve order with id '" + orderId + "'. SQLException");
        }
        return orders;
    }

}
