package com.api.fitzone.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.fitzone.models.ClaseModel;
import com.api.fitzone.services.ClaseService;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public ResponseEntity<ArrayList<ClaseModel>> obtenerClases() {
        return ResponseEntity.ok(claseService.obtenerClases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseModel> obtenerClasePorId(@PathVariable Long id) {
        return claseService.obtenerClasePorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClaseModel> guardarClase(@RequestBody ClaseModel clase) {
        ClaseModel nuevaClase = claseService.guardarClase(clase);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaClase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseModel> actualizarClase(@PathVariable Long id, @RequestBody ClaseModel clase) {
        return ResponseEntity.ok(claseService.actualizarClase(id, clase));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return ResponseEntity.noContent().build();
    }
}
