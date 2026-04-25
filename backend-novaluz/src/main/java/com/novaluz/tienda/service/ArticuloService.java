package com.novaluz.tienda.service;

import com.novaluz.tienda.dto.ArticuloRequest;
import com.novaluz.tienda.dto.ArticuloResponse;
import com.novaluz.tienda.entity.Articulo;
import com.novaluz.tienda.entity.Categoria;
import com.novaluz.tienda.exception.DuplicateResourceException;
import com.novaluz.tienda.exception.ResourceNotFoundException;
import com.novaluz.tienda.repository.ArticuloRepository;
import com.novaluz.tienda.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ArticuloResponse> listarTodos() {
        return articuloRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<ArticuloResponse> listarPorCategoria(Long categoriaId) {
        return articuloRepository.findByCategoriaId(categoriaId).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<ArticuloResponse> buscarPorNombre(String nombre) {
        return articuloRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<ArticuloResponse> listarConStock() {
        return articuloRepository.findByStockGreaterThan(0).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public ArticuloResponse buscarPorId(Long id) {
        Articulo art = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado con ID: " + id));
        return convertirAResponse(art);
    }

    @Transactional
    public ArticuloResponse guardar(ArticuloRequest request) {
        if (articuloRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException("El artículo '" + request.getNombre() + "' ya existe");
        }
        Articulo articulo = convertirAEntidad(request);
        Articulo guardado = articuloRepository.save(articulo);
        return convertirAResponse(guardado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!articuloRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Artículo no encontrado");
        }
        articuloRepository.deleteById(id);
    }

    private ArticuloResponse convertirAResponse(Articulo entidad) {
        ArticuloResponse response = new ArticuloResponse();
        response.setId(entidad.getId());
        response.setNombre(entidad.getNombre());
        response.setDescripcion(entidad.getDescripcion());
        response.setPrecio(entidad.getPrecio());
        response.setStock(entidad.getStock());
        response.setPesoProducto(entidad.getPesoProducto());
        response.setPesoEmbalaje(entidad.getPesoEmbalaje());
        response.setProdLargo(entidad.getProdLargo());
        response.setProdAncho(entidad.getProdAncho());
        response.setProdAlto(entidad.getProdAlto());
        response.setPackLargo(entidad.getPackLargo());
        response.setPackAncho(entidad.getPackAncho());
        response.setPackAlto(entidad.getPackAlto());
        response.setColor(entidad.getColor());
        response.setImagenUrl(entidad.getImagenUrl());
        response.setDisponible(entidad.isDisponible());
        if (entidad.getCategoria() != null) {
            response.setIdCategoria(entidad.getCategoria().getId());
            response.setNombreCategoria(entidad.getCategoria().getNombre());
        }
        return response;
    }

    private Articulo convertirAEntidad(ArticuloRequest request) {
        Articulo entidad = new Articulo();
        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());
        entidad.setPrecio(request.getPrecio());
        entidad.setStock(request.getStock());
        entidad.setPesoProducto(request.getPesoProducto());
        entidad.setPesoEmbalaje(request.getPesoEmbalaje());
        entidad.setProdLargo(request.getProdLargo());
        entidad.setProdAncho(request.getProdAncho());
        entidad.setProdAlto(request.getProdAlto());
        entidad.setPackLargo(request.getPackLargo());
        entidad.setPackAncho(request.getPackAncho());
        entidad.setPackAlto(request.getPackAlto());
        entidad.setColor(request.getColor());
        entidad.setImagenUrl(request.getImagenUrl());
        
        if (request.getIdCategoria() != null) {
            Categoria cat = categoriaRepository.findById(request.getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            entidad.setCategoria(cat);
        }
        return entidad;
    }
}
