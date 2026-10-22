package com.api.fitzone.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.api.fitzone.models.InscripcionModel;
import com.api.fitzone.models.RecordatorioEvent;
import com.api.fitzone.repositories.IInscripcionRepository;

@Component 
public class RecordatorioScheduler {
    @Autowired 
    IInscripcionRepository rInscripcion;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public void programarRecordatorios(){
        LocalDateTime ahora = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime limite = ahora.plusMinutes(1);

        List<InscripcionModel> inscripciones = rInscripcion.findByActivoTrue();

        /*
            Aunque en el ejercicio se recorren todas las inscripciones con un ciclo for, en un entorno real
            es una decisión bastante torpe. No obsante, requiere el uso de Streams, Lambdas y programación funcional.
        */
        for(InscripcionModel inscripcion : inscripciones){
            var clase = inscripcion.getClase();

            LocalDate fecha = ahora.plusHours(2).toLocalDate().with(TemporalAdjusters.nextOrSame(clase.getDia()));

            LocalDateTime fechaClase = fecha.atTime(clase.getInicio());
            LocalDateTime fechaRecordatorio = fechaClase.minusHours(2);

            if(fechaRecordatorio.isBefore(ahora)){
                fecha = fecha.plusWeeks(1);
                fechaClase = fecha.atTime(clase.getInicio());
                fechaRecordatorio = fechaClase.minusHours(2);
            }

            if(!fechaRecordatorio.isBefore(ahora) && fechaRecordatorio.isBefore(limite)){
                eventPublisher.publishEvent(new RecordatorioEvent(inscripcion.getId(), fechaClase, fechaRecordatorio, inscripcion.getSocio().getNombre()));
            }
        }
    }
}
