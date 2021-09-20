package org.example.helpers;

import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.viewmodels.ProductViewModel;

import java.util.List;

/**
 * Product segmentation helper. Used for data preparation for displaying
 */
public class ProductSegmentationHelper {

    public static String[] segmentOrderedProductListToStringArray(List<OrderedProductViewModel> productList) {
        String[] productStrings = new String[productList.size()];

        for (int i = 0; i < productStrings.length; i++) {
            OrderedProductViewModel product = productList.get(i);

            int productId = product.getId();
            String productName = product.getName();
            int quantity = product.getQuantity();

            productStrings[i] = "   " + productId + "      " + productName + "           " + quantity;
        }

        return productStrings;
    }

    public static String[] segmentProductListToStringArray(List<ProductViewModel> productList) {
        String[] productStrings = new String[productList.size()];

        for (int i = 0; i < productStrings.length; i++) {
            ProductViewModel product = productList.get(i);

            int productId = product.getId();
            String productName = product.getName();
            int productPrice = product.getPrice();
            String productStatus = product.getStatus();

            productStrings[i] = "   " + productId + "      " + productName + "           " + productPrice + "           " + productStatus;
        }

        return productStrings;
    }
}
