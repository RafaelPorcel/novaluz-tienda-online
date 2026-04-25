package com.novaluz.tienda.service;

import com.novaluz.tienda.dto.CategoriaRequest;
import com.novaluz.tienda.dto.CategoriaResponse;
import com.novaluz.tienda.entity.Categoria;
import com.novaluz.tienda.exception.DuplicateResourceException;
import com.novaluz.tienda.exception.ResourceNotFoundException;
import com.novaluz.tienda.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<CategoriaResponse> listarPrincipales() {
        return categoriaRepository.findByPadreIsNull().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<CategoriaResponse> listarPorPadre(Long padreId) {
        return categoriaRepository.findByPadreId(padreId).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponse buscarPorId(Long id) {
        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        return convertirAResponse(cat);
    }

    @Transactional
    public CategoriaResponse guardar(CategoriaRequest request) {
        if (categoriaRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException("La categoría '" + request.getNombre() + "' ya existe");
        }
        Categoria categoria = convertirAEntidad(request);
        Categoria guardada = categoriaRepository.save(categoria);
        return convertirAResponse(guardada);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaResponse convertirAResponse(Categoria entidad) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(entidad.getId());
        response.setNombre(entidad.getNombre());
        response.setDescripcion(entidad.getDescripcion());
        if (entidad.getPadre() != null) {
            response.setIdPadre(entidad.getPadre().getId());
            response.setNombrePadre(entidad.getPadre().getNombre());
        }
        return response;
    }

    private Categoria convertirAEntidad(CategoriaRequest request) {
        Categoria entidad = new Categoria();
        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());
        if (request.getIdPadre() != null) {
            Categoria padre = categoriaRepository.findById(request.getIdPadre())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría padre no encontrada"));
            entidad.setPadre(padre);
        }
        return entidad;
    }
}
