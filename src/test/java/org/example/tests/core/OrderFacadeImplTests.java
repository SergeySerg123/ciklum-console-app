package org.example.tests.core;

import org.example.core.background.BackgroundTaskRunner;
import org.example.core.facades.OrderFacade;
import org.example.core.facades.OrderFacadeImpl;
import org.example.core.logger.Logger;
import org.example.core.scanner.ScannerProvider;
import org.example.models.Order;
import org.example.models.viewmodels.OrderViewModel;
import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.viewmodels.ProductViewModel;
import org.example.services.interfaces.OrderService;
import org.example.services.interfaces.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class OrderFacadeImplTests {

    private OrderFacade orderFacade;

    @Mock
    private Logger logger;

    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @Mock
    private ScannerProvider scannerProvider;

    @Mock
    private BackgroundTaskRunner backgroundTaskRunner;

    @Test
    public void provideOrderCreation_PassValidArguments_Created() {
        String scannerFakeData = "\n testing \n 1, 2, 3 \n 10 \n 15 \n 20 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductById(anyInt()))
                .thenReturn(new ProductViewModel(1, "first", 200, "in_stock"))
                .thenReturn(new ProductViewModel(2, "second", 400, "in_stock"))
                .thenReturn(new ProductViewModel(3, "third", 600, "in_stock"));

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderCreation();

        verify(scannerProvider, times(1)).provideScanner();
        verify(orderService, times(1)).createOrder(isA(Order.class));
        verify(productService, times(3)).getProductById(anyInt());
        verify(logger, times(15)).printInfoMessage(anyString());
        verify(logger, never()).printWarningMessage(anyString());
        verify(logger, never()).printDangerMessage(anyString());
    }

    @Test
    public void provideOrderCreation_Pass2InvalidProductIDsAndThenValidArguments_Created() {
        String scannerFakeData = "\n testing \n fake_id_1 \n fake_id_2 \n 1, 2, 3 \n 10 \n 15 \n 20 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductById(anyInt()))
                .thenReturn(new ProductViewModel(1, "first", 200, "in_stock"))
                .thenReturn(new ProductViewModel(2, "second", 400, "in_stock"))
                .thenReturn(new ProductViewModel(3, "third", 600, "in_stock"));

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderCreation();

        verify(scannerProvider, times(1)).provideScanner();
        verify(orderService, times(1)).createOrder(isA(Order.class));
        verify(productService, times(3)).getProductById(anyInt());
        verify(logger, times(19)).printInfoMessage(anyString());
        verify(logger, never()).printWarningMessage(anyString());
        verify(logger, times(2)).printDangerMessage(anyString());
    }

    @Test
    public void provideOrderCreation_Pass2InvalidProductIDsAnd1OutOfStockProduct_Created() {
        String scannerFakeData = "\n testing \n fake_id_1 \n fake_id_2 \n 1, 2, 3 \n 10 \n 15 \n 20 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductById(anyInt()))
                .thenReturn(new ProductViewModel(1, "first", 200, "in_stock"))
                .thenReturn(new ProductViewModel(2, "second", 400, "in_stock"))
                .thenReturn(new ProductViewModel(3, "third", 600, "out_of_stock"));

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderCreation();

        verify(scannerProvider, times(1)).provideScanner();
        verify(orderService, times(1)).createOrder(isA(Order.class));
        verify(productService, times(3)).getProductById(anyInt());
        verify(logger, times(15)).printInfoMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
        verify(logger, times(2)).printDangerMessage(anyString());
    }

    @Test
    public void provideOrderCreation_Pass3OutOfStockProducts_NotCreated() {
        String scannerFakeData = "\n testing \n 1, 2, 3 \n 10 \n 15 \n 20 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductById(anyInt()))
                .thenReturn(new ProductViewModel(1, "first", 200, "out_of_stock"))
                .thenReturn(new ProductViewModel(2, "second", 400, "out_of_stock"))
                .thenReturn(new ProductViewModel(3, "third", 600, "out_of_stock"));

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderCreation();

        verify(scannerProvider, times(1)).provideScanner();
        verify(orderService, never()).createOrder(isA(Order.class));
        verify(productService, times(3)).getProductById(anyInt());
        verify(logger, times(3)).printInfoMessage(anyString());
        verify(logger, times(3)).printWarningMessage(anyString());
        verify(logger, never()).printDangerMessage(anyString());
    }

    @Test
    public void provideOrderById_PassValidArgument_Returned() {
        String scannerFakeData = "1";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(orderService.getOrderById(anyInt())).thenReturn(getOrderViewModelsList());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderById();

        verify(scannerProvider, times(1)).provideScanner();
        verify(orderService, times(1)).getOrderById(anyInt());
        verify(logger, times(3)).printInfoMessage(anyString());
        verify(logger, never()).printWarningMessage(anyString());
    }

    @Test
    public void provideOrderById_ReturnsEmptyList_NotFoundMessage() {
        String scannerFakeData = "1";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(orderService.getOrderById(anyInt())).thenReturn(new ArrayList<>());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderById();

        verify(scannerProvider, times(1)).provideScanner();
        verify(orderService, times(1)).getOrderById(anyInt());
        verify(logger, times(1)).printInfoMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
    }

    @Test
    public void provideOrderById_ReturnsNull_NotFoundMessage() {
        String scannerFakeData = "1";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(orderService.getOrderById(anyInt())).thenReturn(null);

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderById();

        verify(scannerProvider, times(1)).provideScanner();
        verify(orderService, times(1)).getOrderById(anyInt());
        verify(logger, times(1)).printInfoMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
    }

    @Test
    public void provideOrders_PassValidArgument_Returned() {
        when(orderService.getOrders()).thenReturn(getOrderViewModelsList());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrders();

        verify(orderService, times(1)).getOrders();
        verify(logger, times(2)).printInfoMessage(anyString());
        verify(logger, never()).printWarningMessage(anyString());
    }

    @Test
    public void provideOrders_ReturnsEmptyList_NotFoundMessage() {
        when(orderService.getOrders()).thenReturn(new ArrayList<>());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrders();

        verify(orderService, times(1)).getOrders();
        verify(logger, never()).printInfoMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
    }

    @Test
    public void provideOrders_ReturnsNull_NotFoundMessage() {
        when(orderService.getOrders()).thenReturn(null);

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrders();

        verify(orderService, times(1)).getOrders();
        verify(logger, never()).printInfoMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
    }

    @Test
    public void provideOrderQuantityUpdates_PassValidData_Updated() {
        String scannerFakeData = "\n 1 \n 1 \n 1 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductsForQuantityUpdateByOrderId(anyInt())).thenReturn(getOrderedViewModelList());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderQuantityUpdates();

        verify(scannerProvider, times(1)).provideScanner();
        verify(logger, never()).printDangerMessage(anyString());
        verify(logger, never()).printWarningMessage(anyString());
        verify(logger, times(7)).printInfoMessage(anyString());
        verify(backgroundTaskRunner, times(1)).runTaskInBackground(isA(ProductService.class), anyInt(), anyInt());
    }

    @Test
    public void provideOrderQuantityUpdates_PassNegativeQuantity_NotUpdated() {
        String scannerFakeData = "\n 1 \n 1 \n -20 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductsForQuantityUpdateByOrderId(anyInt())).thenReturn(getOrderedViewModelList());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderQuantityUpdates();

        verify(scannerProvider, times(1)).provideScanner();
        verify(logger, times(1)).printDangerMessage(anyString());
        verify(logger, never()).printWarningMessage(anyString());
        verify(logger, times(7)).printInfoMessage(anyString());
        verify(backgroundTaskRunner, never()).runTaskInBackground(isA(ProductService.class), anyInt(), anyInt());
    }

    @Test
    public void provideOrderQuantityUpdates_PassNotExistsProductId_NotUpdated() {
        String scannerFakeData = "\n 1 \n 5 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductsForQuantityUpdateByOrderId(anyInt())).thenReturn(getOrderedViewModelList());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderQuantityUpdates();

        verify(scannerProvider, times(1)).provideScanner();
        verify(logger, never()).printDangerMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
        verify(logger, times(5)).printInfoMessage(anyString());
        verify(backgroundTaskRunner, never()).runTaskInBackground(isA(ProductService.class), anyInt(), anyInt());
    }

    @Test
    public void provideOrderQuantityUpdates_ReturnsEmptyOrderedList_NotUpdated() {
        String scannerFakeData = "\n 1 \n 5 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductsForQuantityUpdateByOrderId(anyInt())).thenReturn(new ArrayList<>());

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderQuantityUpdates();

        verify(scannerProvider, times(1)).provideScanner();
        verify(logger, never()).printDangerMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
        verify(logger, times(1)).printInfoMessage(anyString());
        verify(backgroundTaskRunner, never()).runTaskInBackground(isA(ProductService.class), anyInt(), anyInt());
    }

    @Test
    public void provideOrderQuantityUpdates_ReturnsNullOrderedList_NotUpdated() {
        String scannerFakeData = "\n 1 \n 5 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductsForQuantityUpdateByOrderId(anyInt())).thenReturn(null);

        orderFacade = new OrderFacadeImpl(logger, productService, orderService, scannerProvider, backgroundTaskRunner);
        orderFacade.provideOrderQuantityUpdates();

        verify(scannerProvider, times(1)).provideScanner();
        verify(logger, never()).printDangerMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
        verify(logger, times(1)).printInfoMessage(anyString());
        verify(backgroundTaskRunner, never()).runTaskInBackground(isA(ProductService.class), anyInt(), anyInt());
    }

    private List<OrderViewModel> getOrderViewModelsList() {
        List<OrderViewModel> orderViewModelList = new ArrayList<>();

        orderViewModelList.add(new OrderViewModel(1, "first", 2500, 5, "10:20"));

        return orderViewModelList;
    }

    private List<OrderedProductViewModel> getOrderedViewModelList() {
        List<OrderedProductViewModel> orderedViewModelList = new ArrayList<>();

        orderedViewModelList.add(new OrderedProductViewModel(1, "first", 25));
        orderedViewModelList.add(new OrderedProductViewModel(2, "second", 20));

        return orderedViewModelList;
    }
}
