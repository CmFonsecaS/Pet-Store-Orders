package com.example.petstore_orders.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderItemDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String productName;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;
    
    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private Integer unitPrice;
}
