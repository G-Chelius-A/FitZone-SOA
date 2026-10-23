package com.api.fitzone.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "recordatorios")
public class RecordatorioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recordatorio")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inscripcion", nullable = false)
    @JsonIgnoreProperties("recordatorios")
    private InscripcionModel inscripcion;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "enviado", nullable = false)
    private Boolean enviado;

    // Al crear, la fecha de recordatorio será en 2 horas. También, el recordatorio
    // será falso momentáneamente

    @PrePersist
    public void onCreate() {
        if (this.fecha == null) {
            this.fecha = inscripcion.getFechaInscripcion().minusHours(2);
        }
        if (this.enviado == null) {
            this.enviado = false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InscripcionModel getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(InscripcionModel inscripcion) {
        this.inscripcion = inscripcion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public RecordatorioModel() {
    }

    public RecordatorioModel(Long id, InscripcionModel inscripcion, String mensaje, LocalDateTime fecha,
            Boolean enviado) {
        this.id = id;
        this.inscripcion = inscripcion;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.enviado = enviado;
    }

}
