package org.example.models;

import java.util.List;

/**
 * Represents order before saving to database
 */
public class Order {
    private int id;

    private int userId;

    private String status;

    private List<Integer> productIds;

    private List<OrderDetail> orderDetails;

    private String createdAt;

    public Order(String status, List<Integer> productIds, List<OrderDetail> orderDetails, String createdAt) {
        this.status = status;
        this.productIds = productIds;
        this.orderDetails = orderDetails;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
