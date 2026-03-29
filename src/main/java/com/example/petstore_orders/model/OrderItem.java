package com.example.petstore_orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
}
