package com.api.fitzone.models;

import java.time.LocalDateTime;

/*
 * Recordatorio event.
 * Es de tipo record que no requiere tener getters, setters ni constructores. O sea, están embebidos en él.
 */
public record RecordatorioEvent(
        Long idInscripcion,
        LocalDateTime fechaClase,
        LocalDateTime fechaRecodatorio,
        String nombre) {
}
