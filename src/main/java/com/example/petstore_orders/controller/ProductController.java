package com.example.petstore_orders.controller;

import com.example.petstore_orders.dto.ProductDTO;
import com.example.petstore_orders.model.Product;
import com.example.petstore_orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final OrderService orderService;

    public ProductController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        log.info("Llamada a GET /api/products");
        return orderService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Llamada a GET /api/products/{}", id);
        return ResponseEntity.ok(orderService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("Llamada a POST /api/products para: {}", productDTO.getName());
        return new ResponseEntity<>(orderService.saveProduct(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        log.info("Llamada a PUT /api/products/{}", id);
        return ResponseEntity.ok(orderService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Llamada a DELETE /api/products/{}", id);
        orderService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        log.info("Llamada a GET /api/products/category/{}", category);
        return orderService.getProductsByCategory(category);
    }
}
