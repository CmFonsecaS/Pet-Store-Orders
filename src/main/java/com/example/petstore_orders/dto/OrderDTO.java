package com.example.petstore_orders.dto;

import com.example.petstore_orders.model.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private Long id; // Para respuestas

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String customerName;
    
    @NotBlank(message = "El email del cliente es obligatorio")
    @Email(message = "Formato de email inválido")
    private String customerEmail;
    
    @NotBlank(message = "La dirección de envío es obligatoria")
    private String shippingAddress;
    
    @NotNull(message = "El monto total es obligatorio")
    @Min(value = 0, message = "El monto total no puede ser negativo")
    private Integer totalAmount;
    
    @NotNull(message = "El estado de la orden es obligatorio")
    private OrderStatus status;
    
    @NotEmpty(message = "La orden debe tener al menos un ítem")
    @Valid
    private List<OrderItemDTO> items;
}
