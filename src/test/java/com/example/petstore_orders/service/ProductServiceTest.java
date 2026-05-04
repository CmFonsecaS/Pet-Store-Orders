package com.example.petstore_orders.service;

import com.example.petstore_orders.dto.ProductDTO;
import com.example.petstore_orders.exception.ResourceNotFoundException;
import com.example.petstore_orders.model.Product;
import com.example.petstore_orders.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService; // ProductService methods are inside OrderService

    private Product testProduct;
    private ProductDTO testProductDTO;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(10L);
        testProduct.setName("Juguete Gato");
        testProduct.setCategory("Juguetes");
        testProduct.setPrice(1500);

        testProductDTO = new ProductDTO();
        testProductDTO.setName("Juguete Perro");
        testProductDTO.setCategory("Juguetes");
        testProductDTO.setPrice(2000);
    }

    @AfterEach
    void tearDown() {
        testProduct = null;
        testProductDTO = null;
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        // Act
        List<Product> products = orderService.getAllProducts();

        // Assert
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Juguete Gato", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductsByCategory() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        // Act
        List<Product> products = orderService.getProductsByCategory("Juguetes");

        // Assert
        assertFalse(products.isEmpty());
        assertEquals("Juguetes", products.get(0).getCategory());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        when(productRepository.findById(10L)).thenReturn(Optional.of(testProduct));

        // Act
        orderService.deleteProduct(10L);

        // Assert
        verify(productRepository, times(1)).findById(10L);
        verify(productRepository, times(1)).delete(testProduct);
    }
}
