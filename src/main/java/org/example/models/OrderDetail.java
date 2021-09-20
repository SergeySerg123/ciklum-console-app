package org.example.models;

/**
 * Represents order details in the
 * @see Order instance before saving to database
 */
public class OrderDetail {
    private int productId;

    private int quantity;

    public OrderDetail(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
