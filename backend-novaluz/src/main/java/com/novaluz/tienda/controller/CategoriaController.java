package com.novaluz.tienda.controller;

import com.novaluz.tienda.dto.CategoriaRequest;
import com.novaluz.tienda.dto.CategoriaResponse;
import com.novaluz.tienda.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/principales")
    public ResponseEntity<List<CategoriaResponse>> listarPrincipales() {
        return ResponseEntity.ok(categoriaService.listarPrincipales());
    }

    @GetMapping("/padre/{id}")
    public ResponseEntity<List<CategoriaResponse>> listarPorPadre(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.listarPorPadre(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse nueva = categoriaService.guardar(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtenerPorId(@PathVariable Long id) {
        CategoriaResponse categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }
}
