package org.example.helpers;

import org.example.models.viewmodels.OrderViewModel;

import java.util.List;

/**
 * Order segmentation helper. Used for data preparation for displaying
 */
public class OrderSegmentationHelper {

    public static String[] segmentOrderViewModelListToStringArray(List<OrderViewModel> orderViewModelList) {
        String[] productStrings = new String[orderViewModelList == null ? 0 : orderViewModelList.size()];

        for (int i = 0; i < productStrings.length; i++) {
            OrderViewModel order = orderViewModelList.get(i);

            int orderId = order.getOrderId();
            String productName = order.getProductName();
            int totalPrice = order.getTotalPrice();
            int quantity = order.getProductQuantity();
            String orderTime = order.getOrderTime();

            productStrings[i] = "   " + orderId + "      " + totalPrice + "           " + productName + "                " + quantity + "             " + orderTime;
        }

        return productStrings;
    }
}
