package com.api.fitzone.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.fitzone.models.SocioModel;
import com.api.fitzone.repositories.ISocioRepository;

@Service 
public class ValidaMembresiaService {
    @Autowired 
    ISocioRepository rSocio;
    
    public void validarMembresia(Long id){
        SocioModel socio = rSocio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el socio con id: "+ id));
        
        if(socio.getVigenciaMembresia()==null || socio.getVigenciaMembresia().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El socio no tiene una membresía vigente.");
        }
    }
}
