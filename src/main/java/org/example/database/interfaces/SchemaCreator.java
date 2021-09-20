package org.example.database.interfaces;

/**
 * Performs schema creations during application running
 */
public interface SchemaCreator {

    /**
     * Creates products table schema
     */
    void createProductsTable();

    /**
     * Creates orders table schema
     */
    void createOrdersTable();

    /**
     * Creates order_items table schema
     */
    void createOrderItemsTable();
}
