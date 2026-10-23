package com.api.fitzone.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.fitzone.models.InscripcionModel;
import com.api.fitzone.models.InscritoEvent;
import com.api.fitzone.repositories.IInscripcionRepository;

import jakarta.transaction.Transactional;

@Service 
public class InscripcionService {
    @Autowired 
    IInscripcionRepository rInscripcion;

    @Autowired 
    ValidaMembresiaService sMembresia;

    @Autowired 
    ValidaCupoService sCupo;

    @Autowired 
    ApplicationEventPublisher eventPublisher;

    public ArrayList<InscripcionModel> obtenerInscripciones(){
        return (ArrayList<InscripcionModel>) rInscripcion.findAll();
    }

    public Optional<InscripcionModel> obtenerInscripcionId(Long id){
        return rInscripcion.findById(id);
    }

    @Transactional 
    public InscripcionModel guardarInscripcion(InscripcionModel inscripcion){

        // Validación de nulidad de datos de entrada
        if(inscripcion==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos vacíos o nulos");
        }

        // Validación de relaciones obligatorias en la entidad
        if(inscripcion.getSocio()==null||inscripcion.getClase()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes especificar el socio y la clase");
        }

        // [SOA - Coordinación de Servicios Especializados]
        // 1. Valida que el socio cuente con membresía activa y vigente
        sMembresia.validarMembresia(inscripcion.getSocio().getId());

        // 2. Valida que la clase solicitada aún cuente con cupos disponibles
        sCupo.validarCupo(inscripcion.getClase().getId());

        // Establecer estado activo por defecto y persistir la inscripción
        inscripcion.setActivo(true);
        InscripcionModel guardado = rInscripcion.save(inscripcion);

        // [Arquitectura Orientada a Eventos / EDA]
        // Publicación del evento de dominio 'InscritoEvent' tras registrar exitosamente.
        // Los listeners suscritos (p. ej. registro en logs/notificación al usuario) procesarán este evento.
        eventPublisher.publishEvent(new InscritoEvent(guardado.getId(), guardado.getSocio().getId(), guardado.getClase().getId(), guardado.getFechaInscripcion(), guardado.getSocio().getEmail()));

        return guardado;
    }


    @Transactional
    public InscripcionModel actualizarInscripcion(
            Long id, InscripcionModel datos) {

        InscripcionModel inscripcion = rInscripcion.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se ha encontrado la inscripción con el ID " + id
            ));

        if(datos == null || datos.getClase() == null ||
        datos.getSocio() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debe especificar los datos del socio y la clase."
            );
        }

        boolean cambiaClase =
            !datos.getClase().getId()
                .equals(inscripcion.getClase().getId());

        boolean reactiva =
            !inscripcion.getActivo() && datos.getActivo();

        if(cambiaClase || reactiva) {
            sMembresia.validarMembresia(
                datos.getSocio().getId()
            );
            sCupo.validarCupo(datos.getClase().getId());
        }

        inscripcion.setSocio(datos.getSocio());
        inscripcion.setClase(datos.getClase());
        inscripcion.setActivo(datos.getActivo());

        return rInscripcion.save(inscripcion);
    }


    @Transactional 
    public void elimianrInscripcion(Long id){
        if(!rInscripcion.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la inscripción con el ID: "+id);
        }
        rInscripcion.deleteById(id);
    }

    @Transactional
    public InscripcionModel cancelarInscripcion(Long id) {
        InscripcionModel inscripcion = rInscripcion.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la inscripción con el ID " + id));

        inscripcion.setActivo(false);
        return rInscripcion.save(inscripcion);
    }
}
