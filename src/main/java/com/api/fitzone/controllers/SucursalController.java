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

import com.api.fitzone.models.SucursalModel;
import com.api.fitzone.services.SucursalService;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<ArrayList<SucursalModel>> obtenerSucursales() {
        return ResponseEntity.ok(sucursalService.obtenerSucursales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalModel> obtenerSucursalPorId(@PathVariable Long id) {
        return sucursalService.obtenerSucursalPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SucursalModel> guardarSucursal(@RequestBody SucursalModel sucursal) {
        SucursalModel nuevaSucursal = sucursalService.guardarSucursal(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalModel> actualizarSucursal(@PathVariable Long id, @RequestBody SucursalModel sucursal) {
        return ResponseEntity.ok(sucursalService.actualizarSucursal(id, sucursal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
