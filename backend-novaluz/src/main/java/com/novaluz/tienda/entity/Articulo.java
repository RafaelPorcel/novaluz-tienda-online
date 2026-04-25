package com.novaluz.tienda.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "articulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    // --- Atributos de Peso ---
    private Double pesoProducto;  // Peso neto (kg)
    private Double pesoEmbalaje;  // Peso con caja (kg)

    // --- Dimensiones Producto Montado (cm) ---
    private Double prodLargo;
    private Double prodAncho;
    private Double prodAlto;

    // --- Dimensiones Embalaje/Packaging (cm) ---
    private Double packLargo;
    private Double packAncho;
    private Double packAlto;

    private String color;
    private String imagenUrl;

    // Relación con Categoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Método para comprobar disponibilidad rápidamente
    public boolean isDisponible() {
        return this.stock != null && this.stock > 0;
    }
}
