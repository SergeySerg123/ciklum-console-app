package org.example.core.builders;

import org.example.models.Order;
import org.example.models.OrderDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides mechanism for building
 * @see Order
 */
public class OrderBuilder {
    private String status;

    private List<Integer> productIds = new ArrayList<>();

    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void WithStatus(String status) {
        this.status = status;
    }

    public void WithProductIds(List<Integer> productIds) {
        this.productIds.addAll(productIds);
    }

    public void WithOrderDetails(OrderDetail details) {
        orderDetails.add(details);
    }

    public void excludeProductId(int productId) {
        this.productIds = this.productIds.stream().filter(id -> productId != id).collect(Collectors.toList());
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public Order build() {
        Order order = new Order(status, productIds, orderDetails, generateCreatedAtTime());
        clearFields();
        return order;
    }

    private String generateCreatedAtTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        Date currentDate = new Date();
        return simpleDateFormat.format(currentDate);
    }

    private void clearFields() {
        status = "";
        productIds = new ArrayList<>();
        orderDetails = new ArrayList<>();
    }
}
