package com.foundation.core.controller;

import com.foundation.core.entity.Order;
import com.foundation.core.constants.OrderSide;
import com.foundation.core.constants.OrderStatus;
import com.foundation.core.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private Principal principal;

    private Order testOrder;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testOrder = new Order();
        testOrder.setCustomerId(1L);
        testOrder.setAssetName("AAPL");
        testOrder.setOrderSide(OrderSide.BUY);
        testOrder.setSize(10);
        testOrder.setPrice(BigDecimal.valueOf(150.00));
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setCreateDate(LocalDateTime.now());

        startDate = LocalDateTime.now().minusDays(1);
        endDate = LocalDateTime.now();

        // Mock principal behavior
        when(principal.getName()).thenReturn("testUser");
    }

    @Test
    void createOrder_Success() {
        // Arrange
        when(orderService.createOrder(any(Order.class), any(Principal.class)))
                .thenReturn(testOrder);

        // Act
        ResponseEntity<Order> response = orderController.createOrder(testOrder, principal);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testOrder, response.getBody());
        verify(orderService, times(1)).createOrder(any(Order.class), any(Principal.class));
    }

    @Test
    void listOrders_Success() {
        // Arrange
        List<Order> orderList = Arrays.asList(testOrder);
        when(orderService.listOrders(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Principal.class)))
                .thenReturn(orderList);

        // Act
        ResponseEntity<List<Order>> response = orderController.listOrders(
                1L, startDate, endDate, principal);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(testOrder, response.getBody().get(0));
        verify(orderService, times(1))
                .listOrders(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any(Principal.class));
    }

    @Test
    void cancelOrder_Success() {
        // Arrange
        doNothing().when(orderService).cancelOrder(anyLong(), anyLong(), any(Principal.class));

        // Act
        ResponseEntity<Void> response = orderController.cancelOrder(1L, 1L, principal);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).cancelOrder(anyLong(), anyLong(), any(Principal.class));
    }

    @Test
    void createOrder_ValidationFailure() {
        // Arrange
        Order invalidOrder = new Order();  // Missing required fields
        when(orderService.createOrder(any(Order.class), any(Principal.class)))
                .thenThrow(new RuntimeException("Invalid order"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                orderController.createOrder(invalidOrder, principal));
    }

    @Test
    void listOrders_InvalidDateRange() {
        // Arrange
        LocalDateTime invalidEndDate = startDate.minusDays(1); // End date before start date
        when(orderService.listOrders(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Principal.class)))
                .thenThrow(new IllegalArgumentException("Invalid date range"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                orderController.listOrders(1L, startDate, invalidEndDate, principal));
    }

    @Test
    void cancelOrder_OrderNotFound() {
        // Arrange
        doThrow(new RuntimeException("Order not found"))
                .when(orderService).cancelOrder(anyLong(), anyLong(), any(Principal.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                orderController.cancelOrder(999L, 1L, principal));
    }
}
