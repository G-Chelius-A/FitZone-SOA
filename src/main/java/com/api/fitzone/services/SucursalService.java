package com.api.fitzone.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.api.fitzone.models.SucursalModel;
import com.api.fitzone.repositories.ISucursalRepository;

@Service
public class SucursalService {

    @Autowired
    private ISucursalRepository rSucursal;

    public ArrayList<SucursalModel> obtenerSucursales() {
        return (ArrayList<SucursalModel>) rSucursal.findAll();
    }

    public Optional<SucursalModel> obtenerSucursalPorId(Long id) {
        return rSucursal.findById(id);
    }

    @Transactional
    public SucursalModel guardarSucursal(SucursalModel sucursal) {
        return rSucursal.save(sucursal);
    }

    @Transactional
    public SucursalModel actualizarSucursal(Long id, SucursalModel sucursalDetalles) {
        SucursalModel sucursalExistente = rSucursal.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la sucursal con id: " + id));

        if (sucursalDetalles.getDireccion() != null) {
            sucursalExistente.setDireccion(sucursalDetalles.getDireccion());
        }
        if (sucursalDetalles.getCiudad() != null) {
            sucursalExistente.setCiudad(sucursalDetalles.getCiudad());
        }
        if (sucursalDetalles.getEstado() != null) {
            sucursalExistente.setEstado(sucursalDetalles.getEstado());
        }

        return rSucursal.save(sucursalExistente);
    }

    @Transactional
    public void eliminarSucursal(Long id) {
        if (!rSucursal.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la sucursal con id: " + id);
        }
        rSucursal.deleteById(id);
    }
}
