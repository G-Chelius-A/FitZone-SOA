package com.api.fitzone.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Inscripcion
 */
@Entity
@Table(name = "inscripciones")
public class InscripcionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_socio", nullable = false)
    @JsonIgnoreProperties("inscripciones")
    @NotNull(message = "Debe indicarse un socio")
    private SocioModel socio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_clase", nullable = false)
    @JsonIgnoreProperties("inscripciones")
    @NotNull(message = "Debe indicarse una clase")
    private ClaseModel clase;

    @Column(name = "fecha_inscripcion", nullable = false, updatable = false)
    @NotNull(message = "Debe indicarse una fecha de inscripción")
    private LocalDateTime fechaInscripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "notas")
    private String notas;

    @JsonIgnore
    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("inscripcion")
    private List<RecordatorioModel> recordatorios = new ArrayList<>();

    // Al crear una inscripción, se coloca la fecha y hora actual. También se
    // considera activa.
    @PrePersist
    public void onCreate() {
        this.fechaInscripcion = LocalDateTime.now();
        this.activo = true;
    }

    // Geters, setters y constructores.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SocioModel getSocio() {
        return socio;
    }

    public void setSocio(SocioModel socio) {
        this.socio = socio;
    }

    public ClaseModel getClase() {
        return clase;
    }

    public void setClase(ClaseModel clase) {
        this.clase = clase;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public List<RecordatorioModel> getRecordatorios() {
        return recordatorios;
    }

    public void setRecordatorios(List<RecordatorioModel> recordatorios) {
        this.recordatorios = recordatorios;
    }

    public InscripcionModel() {
    }

    public InscripcionModel(Long id, @NotNull(message = "Debe indicarse un socio") SocioModel socio,
            @NotNull(message = "Debe indicarse una clase") ClaseModel clase,
            @NotNull(message = "Debe indicarse una fecha de inscripción") LocalDateTime fechaInscripcion,
            Boolean activo, String notas, List<RecordatorioModel> recordatorios) {
        this.id = id;
        this.socio = socio;
        this.clase = clase;
        this.fechaInscripcion = fechaInscripcion;
        this.activo = activo;
        this.notas = notas;
        this.recordatorios = recordatorios;
    }

    public InscripcionModel(Long id, @NotNull(message = "Debe indicarse un socio") SocioModel socio,
            @NotNull(message = "Debe indicarse una clase") ClaseModel clase,
            @NotNull(message = "Debe indicarse una fecha de inscripción") LocalDateTime fechaInscripcion,
            Boolean activo) {
        this.id = id;
        this.socio = socio;
        this.clase = clase;
        this.fechaInscripcion = fechaInscripcion;
        this.activo = activo;
    }

}
