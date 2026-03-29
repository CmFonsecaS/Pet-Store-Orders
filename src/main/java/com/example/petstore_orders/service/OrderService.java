package com.example.petstore_orders.service;

import com.example.petstore_orders.model.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Inicializar Productos
        products.add(new Product(1L, "Comida Premium para Perros", "Nutrición", 4500, 120, "Cachupin", "Croquetas ricas en nutrientes para perros activos", "Perros"));
        products.add(new Product(2L, "Varita con Plumas", "Juguetes", 1500, 45, "KittyFun", "Juguete interactivo con plumas naturales", "Gatos"));
        products.add(new Product(3L, "Shampoo Anticaída de Pelo", "Higiene", 2200, 30, "CleanPet", "Shampoo orgánico de aloe vera para piel sensible", "Dog/Cat"));
        products.add(new Product(4L, "Cama Ortopédica para Mascotas", "Comfort", 8900, 15, "PetSafe", "Cama de espuma viscoelástica para mascotas mayores", "Perros/Gatos"));

        // Inicializar Ordenes
        // Orden 1
        List<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem(1L, 1L, "Comida Premium para Perros", 2, 4500));
        items1.add(new OrderItem(2L, 4L, "Cama Ortopédica para Mascotas", 1, 8900));
        orders.add(new Order(101L, LocalDate.now().minusDays(2), "León Osa", "osa@gmail.com", "123 Riesco, Villa Osa", 187, OrderStatus.ENTREGADO, items1));

        // Orden 2
        List<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem(3L, 2L, "Varita con Plumas", 3, 15));
        orders.add(new Order(102L, LocalDate.now().minusDays(1), "Don Gato", "dongato@gmail.com", "456 Meow Ave, New York City", 45, OrderStatus.ENVIADO, items2));

        // Orden 3
        List<OrderItem> items3 = new ArrayList<>();
        items3.add(new OrderItem(4L, 3L, "Anti-Shed Shampoo", 1, 2200));
        orders.add(new Order(103L, LocalDate.now(), "Leonardo Lobos", "leoLobos@gmail.com", "789 Villa Lobos, Zootopia", 22, OrderStatus.PROCESANDO, items3));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(Long id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public OrderStatus getOrderStatus(Long id) {
        Order order = getOrderById(id);
        return (order != null) ? order.getStatus() : null;
    }
}
