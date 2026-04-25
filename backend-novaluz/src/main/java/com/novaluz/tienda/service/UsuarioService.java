package com.novaluz.tienda.service;

import com.novaluz.tienda.dto.UsuarioRequest;
import com.novaluz.tienda.dto.UsuarioResponse;
import com.novaluz.tienda.entity.Usuario;
import com.novaluz.tienda.exception.DuplicateResourceException;
import com.novaluz.tienda.exception.ResourceNotFoundException;
import com.novaluz.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return convertirAResponse(user);
    }

    public UsuarioResponse buscarPorEmail(String email) {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        return convertirAResponse(user);
    }

    @Transactional
    public UsuarioResponse guardar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("El email '" + request.getEmail() + "' ya está registrado");
        }
        Usuario usuario = convertirAEntidad(request);
        Usuario guardado = usuarioRepository.save(usuario);
        return convertirAResponse(guardado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponse convertirAResponse(Usuario entidad) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(entidad.getId());
        response.setEmail(entidad.getEmail());
        response.setNombre(entidad.getNombre());
        response.setApellidos(entidad.getApellidos());
        response.setTelefono(entidad.getTelefono());
        response.setDireccion(entidad.getDireccion());
        response.setRol(entidad.getRol());
        response.setActivo(entidad.isActivo());
        return response;
    }

    private Usuario convertirAEntidad(UsuarioRequest request) {
        Usuario entidad = new Usuario();
        entidad.setEmail(request.getEmail());
        entidad.setPassword(request.getPassword()); 
        entidad.setNombre(request.getNombre());
        entidad.setApellidos(request.getApellidos());
        entidad.setTelefono(request.getTelefono());
        entidad.setDireccion(request.getDireccion());
        entidad.setRol(request.getRol());
        return entidad;
    }
}
