package com.example.petstore_orders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDate orderDate;
    
    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;
    
    @Column(name = "CUSTOMER_EMAIL", nullable = false)
    private String customerEmail;
    
    @Column(name = "SHIPPING_ADDRESS", nullable = false)
    private String shippingAddress;
    
    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Integer totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
