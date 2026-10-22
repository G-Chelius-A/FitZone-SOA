package com.api.fitzone.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.fitzone.models.RecordatorioModel;
import com.api.fitzone.services.RecordatorioService;

@RestController
@RequestMapping("/api/recordatorios")
public class RecordatorioController {

    @Autowired
    private RecordatorioService recordatorioService;

    @GetMapping
    public ResponseEntity<ArrayList<RecordatorioModel>> obtenerRecordatorios() {
        return ResponseEntity.ok(recordatorioService.obtenerRecordatorios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordatorioModel> obtenerRecordatorioPorId(@PathVariable Long id) {
        return recordatorioService.obtenerRecordatorioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
