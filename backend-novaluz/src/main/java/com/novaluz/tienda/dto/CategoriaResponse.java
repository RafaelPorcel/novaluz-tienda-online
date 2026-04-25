package com.novaluz.tienda.dto;

import lombok.Data;

@Data
public class CategoriaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long idPadre;
    private String nombrePadre;
}
