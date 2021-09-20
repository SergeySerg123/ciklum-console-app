package org.example.models.viewmodels;

/**
 * Represents order after retrieving from database
 */
public class OrderViewModel {
    private int orderId;

    private String productName;

    private int totalPrice;

    private int productQuantity;

    private String orderTime;

    public OrderViewModel(int orderId, String productName, int totalPrice, int productQuantity, String orderTime) {
        this.orderId = orderId;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.productQuantity = productQuantity;
        this.orderTime = orderTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getOrderTime() {
        return orderTime;
    }
}
