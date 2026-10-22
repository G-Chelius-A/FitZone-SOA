package com.api.fitzone.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.api.fitzone.models.SocioModel;
import com.api.fitzone.repositories.ISocioRepository;

@Service
public class SocioService {

    @Autowired
    private ISocioRepository rSocio;

    public ArrayList<SocioModel> obtenerSocios() {
        return (ArrayList<SocioModel>) rSocio.findAll();
    }

    public Optional<SocioModel> obtenerSocioPorId(Long id) {
        return rSocio.findById(id);
    }

    @Transactional
    public SocioModel guardarSocio(SocioModel socio) {
        if (socio.getEmail() != null && rSocio.existsByEmail(socio.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un socio registrado con el correo: " + socio.getEmail());
        }
        return rSocio.save(socio);
    }

    @Transactional
    public SocioModel actualizarSocio(Long id, SocioModel socioDetalles) {
        SocioModel socioExistente = rSocio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el socio con id: " + id));

        if (socioDetalles.getEmail() != null 
                && !socioDetalles.getEmail().equalsIgnoreCase(socioExistente.getEmail())
                && rSocio.existsByEmail(socioDetalles.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un socio registrado con el correo: " + socioDetalles.getEmail());
        }

        if (socioDetalles.getNombre() != null) {
            socioExistente.setNombre(socioDetalles.getNombre());
        }
        if (socioDetalles.getEmail() != null) {
            socioExistente.setEmail(socioDetalles.getEmail());
        }
        if (socioDetalles.getTelefono() != null) {
            socioExistente.setTelefono(socioDetalles.getTelefono());
        }
        if (socioDetalles.getVigenciaMembresia() != null) {
            socioExistente.setVigenciaMembresia(socioDetalles.getVigenciaMembresia());
        }

        return rSocio.save(socioExistente);
    }

    @Transactional
    public void eliminarSocio(Long id) {
        if (!rSocio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el socio con id: " + id);
        }
        rSocio.deleteById(id);
    }
}
