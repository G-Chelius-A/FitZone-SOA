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

import org.springframework.transaction.annotation.Transactional;

@Component 
public class RecordatorioEventListener {
    @Autowired 
    IInscripcionRepository rInscripcion;

    @Autowired 
    IRecordatorioRepository rRecordatorio;

    private static final Logger logger = LoggerFactory.getLogger(RecordatorioEventListener.class);


    /**
     * Listener que consume 'RecordatorioEvent'.
     * Aplica reglas de negocio:
     * 1. Verifica estado de la inscripción.
     * 2. Evita registros duplicados de recordatorios para la misma inscripción y fecha.
     * 3. Registra el recordatorio en BD y simula el despacho de la notificación (correo/SMS/push).
     */
    @EventListener 
    @Transactional
    public void generarRecordatorio(RecordatorioEvent evento){
        var inscripcion = rInscripcion.findById(evento.idInscripcion()).orElse(null);

        // Descartar si la inscripción no existe o no se encuentra activa
        if(inscripcion == null || !inscripcion.getActivo()){
            return;
        }

        // Idempotencia: prevenir enviar/guardar dos veces el mismo recordatorio para la misma fecha
        if(rRecordatorio.existsByInscripcion_IdAndFecha(evento.idInscripcion(), evento.fechaRecodatorio())) { return; }

        RecordatorioModel recordatorio = new RecordatorioModel();

        recordatorio.setInscripcion(inscripcion);
        recordatorio.setFecha(evento.fechaRecodatorio());
        String msg = "¡Hola, "+inscripcion.getSocio().getNombre()+". Tu clase de "+inscripcion.getClase().getTipo()+" iniciará en dos horas!";
        recordatorio.setMensaje(msg);
        logger.info(msg);

        /*
            Integración de mensajería externa: aquí se conectaría con un proveedor
            de correo (JavaMailSender / SendGrid), SMS (Twilio) o notificaciones push.
        */
        recordatorio.setEnviado(true);
        rRecordatorio.save(recordatorio);

    }
}
