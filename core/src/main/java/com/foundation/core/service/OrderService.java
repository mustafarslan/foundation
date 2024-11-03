package com.foundation.core.service;

import com.foundation.core.constants.OrderSide;
import com.foundation.core.constants.OrderStatus;
import com.foundation.core.entity.Asset;
import com.foundation.core.entity.Order;
import com.foundation.core.repository.AssetRepository;
import com.foundation.core.repository.CustomerRepository;
import com.foundation.core.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Order createOrder(Order order, Principal principal) {
        // Allow admin to view any customer's orders or restrict to authenticated customer's own orders.
        if (!principal.getName().equals(customerRepository.findByCustomerId(order.getCustomerId()).get().getUsername()) && !isAdmin(principal)) {
            throw new AccessDeniedException("You are not authorized to view these orders");
        }

        // Check if customer has enough TRY or asset to place the order.
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                .orElseThrow(() -> new RuntimeException("TRY asset not found"));

        if (OrderSide.BUY.equals(order.getOrderSide()) && tryAsset.getUsableSize() < order.getPrice().multiply(BigDecimal.valueOf(order.getSize())).intValue()) {
            throw new RuntimeException("Insufficient TRY balance");
        }

        // Save order and update asset sizes accordingly.
        if(OrderSide.BUY.equals(order.getOrderSide())) {
            assetRepository.createOrderToUpdateAsset(order.getCustomerId(), order.getPrice().multiply(BigDecimal.valueOf(order.getSize())).intValue());
        } else {
            assetRepository.cancelOrderToUpdateAsset(order.getCustomerId(), order.getPrice().multiply(BigDecimal.valueOf(order.getSize())).intValue());
        }
        order.setCreateDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public List<Order> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate, Principal principal) {
        // Allow admin to view any customer's orders or restrict to authenticated customer's own orders.
        if (!principal.getName().equals(customerRepository.findByCustomerId(customerId).get().getUsername()) && !isAdmin(principal)) {
            throw new AccessDeniedException("You are not authorized to view these orders");
        }
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    public void cancelOrder(Long orderId, Long customerId, Principal principal) {
        // Allow admin to view any customer's orders or restrict to authenticated customer's own orders.
        if (!principal.getName().equals(customerRepository.findByCustomerId(customerId).get().getUsername()) && !isAdmin(principal)) {
            throw new AccessDeniedException("You are not authorized to view these orders");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        if (!OrderStatus.PENDING.equals(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be canceled");
        }

        // Update asset sizes accordingly and mark as canceled.
        assetRepository.cancelOrderToUpdateAsset(customerId, order.getPrice().multiply(BigDecimal.valueOf(order.getSize())).intValue());
        order.setStatus(OrderStatus.CANCELED);
        order.setCreateDate(LocalDateTime.now());
        orderRepository.save(order);
    }

    // Helper method to check if current user is admin.
    private boolean isAdmin(Principal principal){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
