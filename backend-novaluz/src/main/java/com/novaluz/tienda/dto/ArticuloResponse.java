package com.novaluz.tienda.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ArticuloResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
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
    private Long idCategoria;
    private String nombreCategoria;
    private boolean disponible;
}
