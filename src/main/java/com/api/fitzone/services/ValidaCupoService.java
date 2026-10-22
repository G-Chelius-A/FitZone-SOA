package com.api.fitzone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.fitzone.models.ClaseModel;
import com.api.fitzone.repositories.IClaseRepository;
import com.api.fitzone.repositories.IInscripcionRepository;

@Service 
public class ValidaCupoService {
    @Autowired 
    IClaseRepository rClase;

    @Autowired 
    IInscripcionRepository rInscripcion;

    public void validarCupo(Long idClase){
        ClaseModel clase = rClase.findById(idClase)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado clase con el ID: "+idClase));
    
        Long inscritos = rInscripcion.countByClase_IdClaseAndActivoTrue(idClase);

        if(inscritos >= clase.getCupo()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La clase ya no tiene cupos disponibles");
        }
        
    }
}
