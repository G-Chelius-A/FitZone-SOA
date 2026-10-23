package com.api.fitzone.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.fitzone.models.InscripcionModel;
import com.api.fitzone.services.InscripcionService;

/*
    RestController de las inscripciones
*/
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<ArrayList<InscripcionModel>> obtenerInscripciones() {
        return ResponseEntity.ok(inscripcionService.obtenerInscripciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionModel> obtenerInscripcionPorId(@PathVariable Long id) {
        return inscripcionService.obtenerInscripcionId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InscripcionModel> guardarInscripcion(@RequestBody InscripcionModel inscripcion) {
        InscripcionModel nuevaInscripcion = inscripcionService.guardarInscripcion(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscripcionModel> actualizarInscripcion(@PathVariable Long id,
            @RequestBody InscripcionModel inscripcion) {
        return ResponseEntity.ok(inscripcionService.actualizarInscripcion(id, inscripcion));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<InscripcionModel> cancelarInscripcion(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.cancelarInscripcion(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        inscripcionService.elimianrInscripcion(id);
        return ResponseEntity.noContent().build();
    }
}
