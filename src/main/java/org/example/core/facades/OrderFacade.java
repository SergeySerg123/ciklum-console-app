package org.example.core.facades;

/**
 * Facade for ordering manipulations
 */
public interface OrderFacade {

    /**
     * Provides order creation
     */
    void provideOrderCreation();

    /**
     * Provides order by unique identifier
     */
    void provideOrderById();

    /**
     * Provides orders
     */
    void provideOrders();

    /**
     * Provides product quantity updates mechanism in the order list
     */
    void provideOrderQuantityUpdates();
}
