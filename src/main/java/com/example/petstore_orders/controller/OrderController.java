package com.example.petstore_orders.controller;

import com.example.petstore_orders.dto.OrderDTO;
import com.example.petstore_orders.model.Order;
import com.example.petstore_orders.model.OrderStatus;
import com.example.petstore_orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        log.info("Llamada a GET /api/orders");
        List<EntityModel<Order>> orders = orderService.getAllOrders().stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                        linkTo(methodOn(OrderController.class).getOrderStatus(order.getId())).withRel("status"),
                        linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders")))
                .collect(Collectors.toList());

        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Order>> getOrderById(@PathVariable Long id) {
        log.info("Llamada a GET /api/orders/{}", id);
        Order order = orderService.getOrderById(id);
        EntityModel<Order> resource = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel(),
                linkTo(methodOn(OrderController.class).getOrderStatus(id)).withRel("status"),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Order>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.info("Llamada a POST /api/orders para cliente: {}", orderDTO.getCustomerName());
        Order order = orderService.saveOrder(orderDTO);
        EntityModel<Order> resource = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getOrderStatus(order.getId())).withRel("status"),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Order>> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        log.info("Llamada a PUT /api/orders/{}", id);
        Order order = orderService.updateOrder(id, orderDTO);
        EntityModel<Order> resource = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel(),
                linkTo(methodOn(OrderController.class).getOrderStatus(id)).withRel("status"),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("Llamada a DELETE /api/orders/{}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<EntityModel<Map<String, String>>> getOrderStatus(@PathVariable Long id) {
        log.info("Llamada a GET /api/orders/{}/status", id);
        OrderStatus status = orderService.getOrderStatus(id);
        Map<String, String> response = Collections.singletonMap("status", status.name());
        EntityModel<Map<String, String>> resource = EntityModel.of(response,
                linkTo(methodOn(OrderController.class).getOrderStatus(id)).withSelfRel(),
                linkTo(methodOn(OrderController.class).getOrderById(id)).withRel("order"));
        return ResponseEntity.ok(resource);
    }
}
