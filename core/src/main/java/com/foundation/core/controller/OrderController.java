package com.foundation.core.controller;

import com.foundation.core.entity.Order;
import com.foundation.core.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Orders", description = "Employees can send, list and delete stock orders for their customers")
@RestController
@RequestMapping("/api/orders/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create Order", description = "Create a new order for a given customer, asset, side, size and price.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed"),
            @ApiResponse(responseCode = "404", description = "Not found. Check request or API definition.")
    })
    @PostMapping("v1/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Order> createOrder(@RequestBody Order order, Principal principal) {
        return ResponseEntity.ok(orderService.createOrder(order,principal));
    }

    @Operation(summary = "List Order", description = "List orders for a given customer and date range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed"),
            @ApiResponse(responseCode = "404", description = "Not found. Check request or API definition.")
    })
    @GetMapping("v1/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Order>> listOrders(@RequestParam Long customerId,
                                                  @RequestParam LocalDateTime startDate,
                                                  @RequestParam LocalDateTime endDate,
                                                  Principal principal) {
        return ResponseEntity.ok(orderService.listOrders(customerId, startDate, endDate, principal));
    }

    @Operation(summary = "Delete Order", description = "Cancel a pending order. Other status orders cannot be deleted.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed"),
            @ApiResponse(responseCode = "404", description = "Not found. Check request or API definition.")
    })
    @DeleteMapping("v1/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> cancelOrder(@RequestParam Long orderId, @RequestParam Long customerId, Principal principal) {
        orderService.cancelOrder(orderId, customerId, principal);
        return ResponseEntity.noContent().build();
    }

}
