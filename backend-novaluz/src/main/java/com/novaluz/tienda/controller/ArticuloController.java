package com.novaluz.tienda.controller;

import com.novaluz.tienda.dto.ArticuloRequest;
import com.novaluz.tienda.dto.ArticuloResponse;
import com.novaluz.tienda.service.ArticuloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin(origins = "*")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    public ResponseEntity<List<ArticuloResponse>> listarTodos() {
        List<ArticuloResponse> articulos = articuloService.listarTodos();
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloResponse> obtenerPorId(@PathVariable Long id) {
        // Como el servicio ya devuelve un ArticuloResponse (o lanza excepción),
        // solo tenemos que llamar al método.
        ArticuloResponse articulo = articuloService.buscarPorId(id);
        return ResponseEntity.ok(articulo);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ArticuloResponse>> listarPorCategoria(@PathVariable Long categoriaId) {
        List<ArticuloResponse> articulos = articuloService.listarPorCategoria(categoriaId);
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ArticuloResponse>> buscarPorNombre(@RequestParam String nombre) {
        List<ArticuloResponse> articulos = articuloService.buscarPorNombre(nombre);
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/con-stock")
    public ResponseEntity<List<ArticuloResponse>> listarConStock() {
        List<ArticuloResponse> articulos = articuloService.listarConStock();
        return ResponseEntity.ok(articulos);
    }

    @PostMapping
    public ResponseEntity<ArticuloResponse> crear(@Valid @RequestBody ArticuloRequest request) {
        ArticuloResponse nuevo = articuloService.guardar(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        articuloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
