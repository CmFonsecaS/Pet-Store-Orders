package com.example.petstore_orders.controller;

import com.example.petstore_orders.model.Product;
import com.example.petstore_orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final OrderService orderService;

    public ProductController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return orderService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = orderService.getProductById(id);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return orderService.getProductsByCategory(category);
    }
}
