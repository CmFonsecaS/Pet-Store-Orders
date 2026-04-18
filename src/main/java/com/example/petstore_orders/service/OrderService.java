package com.example.petstore_orders.service;

import com.example.petstore_orders.dto.OrderDTO;
import com.example.petstore_orders.dto.ProductDTO;
import com.example.petstore_orders.exception.ResourceNotFoundException;
import com.example.petstore_orders.model.Order;
import com.example.petstore_orders.model.OrderItem;
import com.example.petstore_orders.model.Product;
import com.example.petstore_orders.model.OrderStatus;
import com.example.petstore_orders.repository.OrderRepository;
import com.example.petstore_orders.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // === PRODUCTOS CRUD ===

    public List<Product> getAllProducts() {
        log.info("Consultando todos los productos");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Product saveProduct(ProductDTO productDTO) {
        log.info("Guardando nuevo producto: {}", productDTO.getName());
        Product product = new Product();
        mapProductDtoToEntity(productDTO, product);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        log.info("Actualizando producto con ID: {}", id);
        Product product = getProductById(id);
        mapProductDtoToEntity(productDTO, product);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public List<Product> getProductsByCategory(String category) {
        log.info("Consultando productos por categoría: {}", category);
        return productRepository.findAll().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // === ORDENES CRUD ===

    public List<Order> getAllOrders() {
        log.info("Consultando todas las órdenes");
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        log.info("Buscando orden con ID: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + id));
    }

    @Transactional
    public Order saveOrder(OrderDTO orderDTO) {
        log.info("Guardando nueva orden para cliente: {}", orderDTO.getCustomerName());
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        mapOrderDtoToEntity(orderDTO, order);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        log.info("Actualizando orden con ID: {}", id);
        Order order = getOrderById(id);
        mapOrderDtoToEntity(orderDTO, order);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        log.info("Eliminando orden con ID: {}", id);
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    public OrderStatus getOrderStatus(Long id) {
        return getOrderById(id).getStatus();
    }

    // === MAPPINGS ===

    private void mapProductDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setBrand(dto.getBrand());
        entity.setDescription(dto.getDescription());
        entity.setPetType(dto.getPetType());
    }

    private void mapOrderDtoToEntity(OrderDTO dto, Order entity) {
        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerEmail(dto.getCustomerEmail());
        entity.setShippingAddress(dto.getShippingAddress());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getStatus());
        
        // Mapeo de items
        if (dto.getItems() != null) {
            List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
                OrderItem item = new OrderItem();
                item.setProductId(itemDto.getProductId());
                item.setProductName(itemDto.getProductName());
                item.setQuantity(itemDto.getQuantity());
                item.setUnitPrice(itemDto.getUnitPrice());
                item.setOrder(entity); // Establecer la referencia bidireccional
                return item;
            }).collect(Collectors.toList());
            
            // valida si la entidad ya tiene items, se limpian para evitar duplicados.
            if (entity.getItems() != null) {
                entity.getItems().clear();
                entity.getItems().addAll(items);
            } else {
                entity.setItems(items);
            }
        }
    }
}
