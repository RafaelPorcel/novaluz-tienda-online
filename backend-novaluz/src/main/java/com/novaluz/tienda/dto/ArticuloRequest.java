package com.novaluz.tienda.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ArticuloRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero
    private BigDecimal precio;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(0)
    private Integer stock;
    
    private Double pesoProducto;
    private Double pesoEmbalaje;
    private Double prodLargo;
    private Double prodAncho;
    private Double prodAlto;
    private Double packLargo;
    private Double packAncho;
    private Double packAlto;
    private String color;
    private String imagenUrl;
    
    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;
}
