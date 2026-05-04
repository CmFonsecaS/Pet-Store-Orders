package com.example.petstore_orders.service;

import com.example.petstore_orders.dto.OrderDTO;
import com.example.petstore_orders.exception.ResourceNotFoundException;
import com.example.petstore_orders.model.Order;
import com.example.petstore_orders.model.OrderStatus;
import com.example.petstore_orders.repository.OrderRepository;
import com.example.petstore_orders.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private OrderDTO testOrderDTO;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada @Test
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setCustomerName("Cliente Prueba");
        testOrder.setStatus(OrderStatus.PROCESANDO);

        testOrderDTO = new OrderDTO();
        testOrderDTO.setCustomerName("Cliente Nuevo");
        testOrderDTO.setStatus(OrderStatus.PENDIENTE);
    }

    @AfterEach
    void tearDown() {
        // Se ejecuta después de cada @Test
        testOrder = null;
        testOrderDTO = null;
    }

    @Test
    void testGetOrderById_Success() {
        // Arrange (Preparar)
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act (Actuar)
        Order foundOrder = orderService.getOrderById(1L);

        // Assert (Verificar)
        assertNotNull(foundOrder);
        assertEquals("Cliente Prueba", foundOrder.getCustomerName());
        assertEquals(OrderStatus.PROCESANDO, foundOrder.getStatus());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        // Arrange
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(99L);
        });

        assertTrue(exception.getMessage().contains("Orden no encontrada"));
        verify(orderRepository, times(1)).findById(99L);
    }

    @Test
    void testSaveOrder() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(2L);
            return savedOrder;
        });

        // Act
        Order createdOrder = orderService.saveOrder(testOrderDTO);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(2L, createdOrder.getId());
        assertEquals("Cliente Nuevo", createdOrder.getCustomerName());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
