package org.example.core.background;

import org.example.services.interfaces.ProductService;

/**
 * Provides opportunity run tasks in separate threads
 */
public interface BackgroundTaskRunner extends Runnable {

    /**
     * Runs task in separate thread
     * @param productService
     * @param orderId
     * @param newQuantity
     */
    void runTaskInBackground(ProductService productService, int orderId, int newQuantity);
}
