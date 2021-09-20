package org.example.tests.services;

import org.checkerframework.checker.units.qual.A;
import org.example.core.logger.Logger;
import org.example.database.interfaces.OrderRepository;
import org.example.models.Order;
import org.example.models.OrderDetail;
import org.example.models.viewmodels.OrderViewModel;
import org.example.services.OrderServiceImpl;
import org.example.services.interfaces.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class OrderServiceTests {

    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Logger logger;

    @Test
    public void createOrder_ValidOrder_Created() throws SQLException {
        Order validOrder = new Order("some_status", getProductIDs(), getOrderDetails(), "10:20");

        orderService = new OrderServiceImpl(orderRepository, logger);
        orderService.createOrder(validOrder);

        verify(orderRepository, times(1)).createOrder(validOrder);
        verify(logger, times(1)).printSuccessMessage(anyString());
        verify(logger, never()).printDangerMessage(anyString());
    }

    @Test
    public void createOrder_OrderRepositoryThrows_NotCreated() throws SQLException {
        Order validOrder = new Order("some_status", getProductIDs(), getOrderDetails(), "10:20");
        doThrow(new SQLException()).when(orderRepository).createOrder(validOrder);

        orderService = new OrderServiceImpl(orderRepository, logger);
        orderService.createOrder(validOrder);

        verify(orderRepository, times(1)).createOrder(validOrder);
        verify(logger, never()).printSuccessMessage(anyString());
        verify(logger, times(1)).printDangerMessage(anyString());
    }

    @Test
    public void getOrders_OrderRepositoryThrows_ReturnsNull() throws SQLException {
        doThrow(new SQLException()).when(orderRepository).getAllOrders();

        orderService = new OrderServiceImpl(orderRepository, logger);
        List<OrderViewModel> orders = orderService.getOrders();

        Assertions.assertNull(orders);
        verify(orderRepository, times(1)).getAllOrders();
        verify(logger, times(1)).printDangerMessage(anyString());
    }

    @Test
    public void getOrders_DoesNotThrows_ReturnsOrders() throws SQLException {
        when(orderRepository.getAllOrders()).thenReturn(getOrders());

        orderService = new OrderServiceImpl(orderRepository, logger);
        List<OrderViewModel> orders = orderService.getOrders();

        verify(orderRepository, times(1)).getAllOrders();
        verify(logger, never()).printDangerMessage(anyString());
        Assertions.assertEquals(2, orders.size());
    }

    @Test
    public void getOrders_DoesNotThrows_ReturnsEmptyList() throws SQLException {
        when(orderRepository.getAllOrders()).thenReturn(new ArrayList<>());

        orderService = new OrderServiceImpl(orderRepository, logger);
        List<OrderViewModel> orders = orderService.getOrders();

        verify(orderRepository, times(1)).getAllOrders();
        verify(logger, never()).printDangerMessage(anyString());
        Assertions.assertEquals(0, orders.size());
    }

    @Test
    public void getOrderById_OrderRepositoryThrows_ReturnsNull() throws SQLException {
        int orderId = 1;
        doThrow(new SQLException()).when(orderRepository).getOrderById(orderId);

        orderService = new OrderServiceImpl(orderRepository, logger);
        List<OrderViewModel> orders = orderService.getOrderById(orderId);

        Assertions.assertNull(orders);
        verify(orderRepository, times(1)).getOrderById(orderId);
        verify(logger, times(1)).printDangerMessage(anyString());
    }

    @Test
    public void getOrderById_DoesNotThrows_ReturnsOrders() throws SQLException {
        int orderId = 1;
        when(orderRepository.getOrderById(orderId)).thenReturn(getOrders());

        orderService = new OrderServiceImpl(orderRepository, logger);
        List<OrderViewModel> orders = orderService.getOrderById(orderId);

        verify(orderRepository, times(1)).getOrderById(orderId);
        verify(logger, never()).printDangerMessage(anyString());
        Assertions.assertEquals(2, orders.size());
    }

    @Test
    public void getOrderById_DoesNotThrows_ReturnsEmptyList() throws SQLException {
        int orderId = 1;
        when(orderRepository.getOrderById(orderId)).thenReturn(new ArrayList<>());

        orderService = new OrderServiceImpl(orderRepository, logger);
        List<OrderViewModel> orders = orderService.getOrderById(orderId);

        verify(orderRepository, times(1)).getOrderById(orderId);
        verify(logger, never()).printDangerMessage(anyString());
        Assertions.assertEquals(0, orders.size());
    }

    private List<OrderViewModel> getOrders() {
        List<OrderViewModel> orders = new ArrayList<>();

        orders.add(new OrderViewModel(1, "first", 200, 2, "10:20"));
        orders.add(new OrderViewModel(2, "second", 200, 2, "10:20"));

        return orders;
    }

    private List<Integer> getProductIDs() {
        List<Integer> productIDs = new ArrayList<>();

        productIDs.add(1);
        productIDs.add(2);

        return productIDs;
    }

    private List<OrderDetail> getOrderDetails() {
        OrderDetail firstOrderDetail = new OrderDetail(1, 10);
        OrderDetail secondOrderDetail = new OrderDetail(2, 10);

        List<OrderDetail> details = new ArrayList<>();

        details.add(firstOrderDetail);
        details.add(secondOrderDetail);

        return details;
    }
}
