package com.api.fitzone.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.api.fitzone.models.InscritoEvent;

@Component 
public class InscritoEventListener {
    private static final Logger logger = LoggerFactory.getLogger(InscritoEventListener.class);

    @TransactionalEventListener 
    public void registrarInscripcion(InscritoEvent evento){
        logger.info("Nueva inscripción: ID={}, Socio={}, Clase={}, Fecha={}. Enviado al correo: "+evento.getCorreo(), evento.getIdInscripcion(), evento.getIdSocio(), evento.getIdClase(), evento.getFecha());
    }
}
