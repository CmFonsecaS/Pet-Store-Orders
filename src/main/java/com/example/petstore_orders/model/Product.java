package com.example.petstore_orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String category;
    private Integer price;
    private Integer stock;
    private String brand;
    private String description;
    private String petType;
}
