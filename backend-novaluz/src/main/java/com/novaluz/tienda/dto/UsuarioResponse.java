package com.novaluz.tienda.dto;

import com.novaluz.tienda.entity.Rol;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String email;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private Rol rol;
    private boolean activo;
}
