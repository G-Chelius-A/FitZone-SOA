package com.api.fitzone.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.fitzone.models.RecordatorioModel;
import com.api.fitzone.repositories.IRecordatorioRepository;

// Servicio de consultas de recordatorios. Útil para mantener alguna trazabilidad.
@Service
public class RecordatorioService {

    @Autowired
    private IRecordatorioRepository rRecordatorio;

    public ArrayList<RecordatorioModel> obtenerRecordatorios() {
        return (ArrayList<RecordatorioModel>) rRecordatorio.findAll();
    }

    public Optional<RecordatorioModel> obtenerRecordatorioPorId(Long id) {
        return rRecordatorio.findById(id);
    }
}
