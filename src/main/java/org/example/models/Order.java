package org.example.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;

    private int userId;

    private String status;

    private LocalDateTime dateTime;

    private List<Integer> productIds;

    public Order(String status, List<Integer> productIds) {
        this.status = status;
        this.productIds = productIds;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
