package com.example.petstore_orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private LocalDate orderDate;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private Integer totalAmount;
    private OrderStatus status;
    private List<OrderItem> items;
}
