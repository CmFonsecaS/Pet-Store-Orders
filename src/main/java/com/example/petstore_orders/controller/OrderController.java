package com.example.petstore_orders.controller;

import com.example.petstore_orders.model.Order;
import com.example.petstore_orders.model.OrderStatus;
import com.example.petstore_orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return (order != null) ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> getOrderStatus(@PathVariable Long id) {
        OrderStatus status = orderService.getOrderStatus(id);
        if (status != null) {
            return ResponseEntity.ok(Collections.singletonMap("status", status.name()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
