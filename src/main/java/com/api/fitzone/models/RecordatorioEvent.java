package com.api.fitzone.models;

import java.time.LocalDateTime;

public record RecordatorioEvent(
    Long idInscripcion,
    LocalDateTime fechaClase,
    LocalDateTime fechaRecodatorio,
    String nombre
) {}
