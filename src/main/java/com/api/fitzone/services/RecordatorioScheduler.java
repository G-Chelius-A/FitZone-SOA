package com.api.fitzone.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.api.fitzone.models.InscripcionModel;
import com.api.fitzone.models.RecordatorioEvent;
import com.api.fitzone.repositories.IInscripcionRepository;

import org.springframework.transaction.annotation.Transactional;

@Component 
public class RecordatorioScheduler {
    @Autowired 
    IInscripcionRepository rInscripcion;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    /**
     * Tarea programada que se ejecuta cada minuto (60,000 ms).
     * Revisa las inscripciones activas y calcula si la próxima sesión de la clase
     * está a 2 horas o menos del tiempo actual. De cumplirse la ventana de tiempo,
     * publica un 'RecordatorioEvent' hacia la mensajería interna.
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void programarRecordatorios(){
        // Normalización temporal al inicio del minuto actual
        LocalDateTime ahora = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime limite = ahora.plusMinutes(1);

        List<InscripcionModel> inscripciones = rInscripcion.findByActivoTrue();

        /*
            Aunque en el ejercicio se recorren todas las inscripciones con un ciclo for, en un entorno real
            es una decisión bastante torpe. No obsante, requiere el uso de Streams, Lambdas y programación funcional.
        */
        for(InscripcionModel inscripcion : inscripciones){
            var clase = inscripcion.getClase();

            // Cálculo del próximo día de la semana correspondiente a la clase (hoy o próxima fecha)
            LocalDate fecha = ahora.plusHours(2).toLocalDate().with(TemporalAdjusters.nextOrSame(clase.getDia()));

            LocalDateTime fechaClase = fecha.atTime(clase.getInicio());
            // El recordatorio debe dispararse 2 horas antes de la clase
            LocalDateTime fechaRecordatorio = fechaClase.minusHours(2);

            // Si el recordatorio calculado para esta semana ya pasó, se traslada a la semana siguiente
            if(fechaRecordatorio.isBefore(ahora)){
                fecha = fecha.plusWeeks(1);
                fechaClase = fecha.atTime(clase.getInicio());
                fechaRecordatorio = fechaClase.minusHours(2);
            }

            // Ventana de disparo: si el recordatorio cae dentro del minuto actual (entre ahora y límite)
            if(!fechaRecordatorio.isBefore(ahora) && fechaRecordatorio.isBefore(limite)){
                // Publicación del evento hacia el bus desacoplado de la aplicación
                eventPublisher.publishEvent(new RecordatorioEvent(inscripcion.getId(), fechaClase, fechaRecordatorio, inscripcion.getSocio().getNombre()));
            }
        }
    }
}
