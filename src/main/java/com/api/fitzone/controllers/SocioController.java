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

import com.api.fitzone.models.SocioModel;
import com.api.fitzone.services.SocioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    @Autowired
    private SocioService socioService;

    @GetMapping
    public ResponseEntity<ArrayList<SocioModel>> obtenerSocios() {
        return ResponseEntity.ok(socioService.obtenerSocios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioModel> obtenerSocioPorId(@PathVariable Long id) {
        return socioService.obtenerSocioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SocioModel> guardarSocio(@Valid @RequestBody SocioModel socio) {
        SocioModel nuevoSocio = socioService.guardarSocio(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocioModel> actualizarSocio(@PathVariable Long id, @RequestBody SocioModel socio) {
        return ResponseEntity.ok(socioService.actualizarSocio(id, socio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long id) {
        socioService.eliminarSocio(id);
        return ResponseEntity.noContent().build();
    }
}
