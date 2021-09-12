package org.example.core;

import org.example.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private String status;

    private List<Integer> productIds = new ArrayList<>();

    public void WithStatus(String status) {
        this.status = status;
    }

    public void WithProductIds(int[] productIds) {
        for (Integer id: productIds) {
            this.productIds.add(id);
        }
    }

    public Order build() {
        return new Order(status, productIds);
    }
}
