package org.example.enums;

/**
 * Product available statuses
 */
public enum ProductStatus {
    /**
     * Product in the stock available for purchases
     */
    in_stock,

    /**
     * Product out of the stock and can't be purchased
     */
    out_of_stock,

    /**
     * Product running low, but can be purchased
     */
    running_low;
}
