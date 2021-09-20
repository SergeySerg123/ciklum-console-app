package org.example.core.background;

import com.google.inject.Inject;
import org.example.services.interfaces.ProductService;

/**
 * Implementation of
 * @see BackgroundTaskRunner
 */
public class BackgroundProductQuantityUpdaterTaskRunner implements BackgroundTaskRunner {

    private final ProductService productService;
    private int orderId;
    private int newQuantity;

    /**
     * The constructor. Used for IoC
     * @param productService
     */
    @Inject
    public BackgroundProductQuantityUpdaterTaskRunner(ProductService productService) {
        this.productService = productService;
    }

    /**
     * The constructor. Used during running the task in the separate thread
     * @param productService
     * @param orderId
     * @param newQuantity
     */
    public BackgroundProductQuantityUpdaterTaskRunner(ProductService productService, Integer orderId, Integer newQuantity) {
        this.productService = productService;
        this.orderId = orderId;
        this.newQuantity = newQuantity;
    }

    @Override
    public void run() {
        this.productService.updateProductQuantityInOrders(orderId, newQuantity);
    }

    /**
     * Runs task in separate thread
     */
    @Override
    public void runTaskInBackground(ProductService productService, int orderId, int newQuantity) {
        new Thread(new BackgroundProductQuantityUpdaterTaskRunner(productService, orderId, newQuantity)).start();
    }
}
