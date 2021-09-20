package org.example.core.facades;

import com.google.inject.Inject;
import org.example.core.background.BackgroundTaskRunner;
import org.example.core.builders.OrderBuilder;
import org.example.core.logger.Logger;
import org.example.core.scanner.ScannerProvider;
import org.example.enums.ProductStatus;
import org.example.helpers.OrderSegmentationHelper;
import org.example.helpers.ProductSegmentationHelper;
import org.example.models.OrderDetail;
import org.example.models.viewmodels.OrderViewModel;
import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.viewmodels.ProductViewModel;
import org.example.services.interfaces.OrderService;
import org.example.services.interfaces.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of
 * @see OrderFacade
 */
public class OrderFacadeImpl implements OrderFacade {
    private boolean isValidOrderStatus;
    private boolean isValidProductIds;

    private final OrderBuilder orderBuilder = new OrderBuilder();

    private final Logger logger;
    private final ProductService productService;
    private final OrderService orderService;
    private final ScannerProvider scannerProvider;
    private final BackgroundTaskRunner backgroundTaskRunner;

    /**
     * The constructor
     * @param logger
     * @param productService
     * @param orderService
     * @param scannerProvider
     * @param backgroundTaskRunner
     */
    @Inject
    public OrderFacadeImpl(Logger logger, ProductService productService, OrderService orderService, ScannerProvider scannerProvider, BackgroundTaskRunner backgroundTaskRunner) {
        this.logger = logger;
        this.productService = productService;
        this.orderService = orderService;
        this.scannerProvider = scannerProvider;
        this.backgroundTaskRunner = backgroundTaskRunner;
    }

    /**
     * Provides order creation
     */
    @Override
    public void provideOrderCreation() {
        resetValidationStates(); // reset validation states before new loop

        Scanner input = scannerProvider.provideScanner();

        input.nextLine(); // move to next line

        while (!isValidOrderStatus) {
            logger.printInfoMessage("Enter a order status, please.");
            String status = input.nextLine();
            isValidOrderStatus(status);
        }

        while (!isValidProductIds) {
            logger.printInfoMessage("Enter the product IDs that need to be added to order.");
            logger.printInfoMessage("Input example: '1, 2, 3' and so on...");

            String productIDs = input.nextLine();

            isValidProductIds(productIDs);
        }

        // Enrich every order with product details
        for (int productId: orderBuilder.getProductIds()) {
            ProductViewModel product = productService.getProductById(productId);

            if (product == null) {
                logger.printWarningMessage("Product with id '" + productId + "' was not found.");
                orderBuilder.excludeProductId(productId);
                continue;
            }

            if (product.getStatus().equals(ProductStatus.out_of_stock.name())) {
                logger.printWarningMessage("Product with id '" + product.getId() + "' is out of stock and can't be ordered.");
                orderBuilder.excludeProductId(product.getId());
                continue;
            }

            logger.printInfoMessage("Enter please quantity for product:");
            logger.printInfoMessage("ProductID: " + product.getId());
            logger.printInfoMessage("Product name: " + product.getName());
            logger.printInfoMessage("Product price: " + product.getPrice());

            int quantity = input.nextInt();

            orderBuilder.WithOrderDetails(new OrderDetail(productId, quantity));
        }

        if (orderBuilder.getOrderDetails().size() > 0)
            orderService.createOrder(orderBuilder.build());
    }

    /**
     * Provides order by unique identifier
     */
    @Override
    public void provideOrderById() {
        Scanner input = scannerProvider.provideScanner();

        logger.printInfoMessage("Enter please order id:");
        int orderId = input.nextInt();

        List<OrderViewModel> orderViewModelList = orderService.getOrderById(orderId);

        String[] orders = OrderSegmentationHelper.segmentOrderViewModelListToStringArray(orderViewModelList);

        if(orders.length == 0) {
            logger.printWarningMessage("Order not found.");
            return;
        }

        logger.printInfoMessage("| Order ID | Products total Price | Product Name | Products Quantity in orderEntry | Order Created Date [YYYY-MM-DD HH:MM ] |");
        for (String order : orders) {
            logger.printInfoMessage(order);
        }

        resetValidationStates();
    }

    /**
     * Provides orders
     */
    @Override
    public void provideOrders() {
        List<OrderViewModel> orderViewModelList = orderService.getOrders();

        String[] orders = OrderSegmentationHelper.segmentOrderViewModelListToStringArray(orderViewModelList);

        if(orders.length == 0) {
            logger.printWarningMessage("No orders.");
            return;
        }

        logger.printInfoMessage("| Order ID | Products total Price | Product Name | Products Quantity in orderEntry | Order Created Date [YYYY-MM-DD HH:MM ] |");
        for (String order : orders) {
            logger.printInfoMessage(order);
        }
    }

    /**
     * Provides product quantity updates mechanism in the order list
     */
    @Override
    public void provideOrderQuantityUpdates() {
        Scanner input = scannerProvider.provideScanner();

        logger.printInfoMessage("Enter please order number for updating product quantity in the order list.");

        int orderId = input.nextInt();

        List<OrderedProductViewModel> orderedProductViewModelList = productService.getProductsForQuantityUpdateByOrderId(orderId);

        if (orderedProductViewModelList == null || orderedProductViewModelList.size() == 0) {
            logger.printWarningMessage("No order with id: " + orderId);
            return;
        }

        logger.printInfoMessage("| Product ID | Product Name | Product quantity |");
        for (String productString : ProductSegmentationHelper.segmentOrderedProductListToStringArray(orderedProductViewModelList)) {
            logger.printInfoMessage(productString);
        }

        logger.printInfoMessage("Select product id for updating the quantity");

        int productId = input.nextInt();

        OrderedProductViewModel orderedProductViewModel = orderedProductViewModelList
                .stream()
                .filter(product -> product.getId() == productId)
                .findAny()
                .orElse(null);

        if (orderedProductViewModel == null) {
            logger.printWarningMessage("Product with id '" + productId + "' not found.");
            return;
        }

        logger.printInfoMessage("Current product quantity in the order list with id '" + orderId + "' is '" +  orderedProductViewModel.getQuantity() + "'");
        logger.printInfoMessage("Input new quantity, please");

        int newQuantity = input.nextInt();

        if (newQuantity < 0) {
            logger.printDangerMessage("Product quantity can't be less then 0.");
            return;
        }

        // run in the separated thread
        backgroundTaskRunner.runTaskInBackground(productService, orderId, newQuantity);
    }

    private void isValidOrderStatus(String status) {
        orderBuilder.WithStatus(status);
        isValidOrderStatus = true;
    }

    private void isValidProductIds(String productIDs) {
        try {
            List<Integer> productIDsList = Arrays.stream(productIDs.split(","))
                    .map(productId -> Integer.parseInt(productId.trim()))
                    .toList();

            orderBuilder.WithProductIds(productIDsList);
            isValidProductIds = true;
        } catch (Exception ignored) {
            logger.printDangerMessage("You entered invalid data. Please, try again.");
        }
    }

    private void resetValidationStates() {
        isValidOrderStatus = false;
        isValidProductIds = false;
    }
}
