package com.api.fitzone.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.api.fitzone.models.ClaseModel;
import com.api.fitzone.repositories.IClaseRepository;

@Service
public class ClaseService {

    @Autowired
    private IClaseRepository rClase;

    public ArrayList<ClaseModel> obtenerClases() {
        return (ArrayList<ClaseModel>) rClase.findAll();
    }

    public Optional<ClaseModel> obtenerClasePorId(Long id) {
        return rClase.findById(id);
    }

    @Transactional
    public ClaseModel guardarClase(ClaseModel clase) {
        return rClase.save(clase);
    }

    @Transactional
    public ClaseModel actualizarClase(Long id, ClaseModel claseDetalles) {
        ClaseModel claseExistente = rClase.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la clase con id: " + id));

        if (claseDetalles.getCupo() != null && claseDetalles.getCupo()<=20) {
            claseExistente.setCupo(claseDetalles.getCupo());
        }
        if (claseDetalles.getInstructor() != null) {
            claseExistente.setInstructor(claseDetalles.getInstructor());
        }
        if (claseDetalles.getTipo() != null) {
            claseExistente.setTipo(claseDetalles.getTipo());
        }
        if (claseDetalles.getInicio() != null) {
            claseExistente.setInicio(claseDetalles.getInicio());
        }
        if (claseDetalles.getFin() != null) {
            claseExistente.setFin(claseDetalles.getFin());
        }
        if (claseDetalles.getDia() != null) {
            claseExistente.setDia(claseDetalles.getDia());
        }
        if (claseDetalles.getSucursal() != null) {
            claseExistente.setSucursal(claseDetalles.getSucursal());
        }

        return rClase.save(claseExistente);
    }

    @Transactional
    public void eliminarClase(Long id) {
        if (!rClase.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la clase con id: " + id);
        }
        rClase.deleteById(id);
    }
}
