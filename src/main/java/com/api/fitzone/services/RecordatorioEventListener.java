package com.api.fitzone.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.api.fitzone.models.RecordatorioEvent;
import com.api.fitzone.models.RecordatorioModel;
import com.api.fitzone.repositories.IInscripcionRepository;
import com.api.fitzone.repositories.IRecordatorioRepository;

@Component 
public class RecordatorioEventListener {
    @Autowired 
    IInscripcionRepository rInscripcion;

    @Autowired 
    IRecordatorioRepository rRecordatorio;

    private static final Logger logger = LoggerFactory.getLogger(RecordatorioEventListener.class);


    @EventListener 
    public void generarRecordatorio(RecordatorioEvent evento){
        var inscripcion = rInscripcion.findById(evento.idInscripcion()).orElse(null);

        if(inscripcion == null || !inscripcion.getActivo()){
            return;
        }

        if(rRecordatorio.existsByInscripcion_IdAndFecha(evento.idInscripcion(), evento.fechaRecodatorio())) { return; }

        RecordatorioModel recordatorio = new RecordatorioModel();

        recordatorio.setInscripcion(inscripcion);
        recordatorio.setFecha(evento.fechaRecodatorio());
        String msg = "¡Hola, "+inscripcion.getSocio().getNombre()+". Tu clase de "+inscripcion.getClase().getTipo()+" iniciará en dos horas!";
        recordatorio.setMensaje(msg);
        logger.info(msg);
        /*
            Aquí se enviaría por correo electrónico o teléfono.
        */
        recordatorio.setEnviado(true);
        rRecordatorio.save(recordatorio);

    }
}
