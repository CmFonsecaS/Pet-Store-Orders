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

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.stream.Collectors;

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
    public CollectionModel<EntityModel<Product>> getAllProducts() {
        log.info("Llamada a GET /api/products");
        List<EntityModel<Product>> products = orderService.getAllProducts().stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable Long id) {
        log.info("Llamada a GET /api/products/{}", id);
        Product product = orderService.getProductById(id);
        EntityModel<Product> resource = EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Product>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("Llamada a POST /api/products para: {}", productDTO.getName());
        Product product = orderService.saveProduct(productDTO);
        EntityModel<Product> resource = EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        log.info("Llamada a PUT /api/products/{}", id);
        Product product = orderService.updateProduct(id, productDTO);
        EntityModel<Product> resource = EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Llamada a DELETE /api/products/{}", id);
        orderService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public CollectionModel<EntityModel<Product>> getProductsByCategory(@PathVariable String category) {
        log.info("Llamada a GET /api/products/category/{}", category);
        List<EntityModel<Product>> products = orderService.getProductsByCategory(category).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getProductsByCategory(category)).withSelfRel());
    }
}
