package com.api.fitzone.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clases")
public class ClaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clase", nullable = false)
    private Long id;

    @Column(name = "cupo", nullable = false)
    private Integer cupo;

    @Column(name = "instructor", nullable = false)
    private String instructor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime inicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime fin;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DayOfWeek dia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    @JsonIgnoreProperties("clases")
    private SucursalModel sucursal;

    @JsonIgnore
    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("clase")
    private List<InscripcionModel> inscripciones = new ArrayList<>();

    // Sólo se admiten estos tipos de clases: YOGA, SPINNING y FUNCIONAL
    public enum Tipo {
        YOGA,
        SPINNING,
        FUNCIONAL
    }
    // Getters, setters y constructores

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setDia(DayOfWeek dia) {
        this.dia = dia;
    }

    public SucursalModel getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalModel sucursal) {
        this.sucursal = sucursal;
    }

    public ClaseModel() {
    }

    public ClaseModel(Long id, Integer cupo, String instructor, Tipo tipo, LocalTime inicio, LocalTime fin,
            DayOfWeek dia, SucursalModel sucursal) {
        this.id = id;
        this.cupo = cupo;
        this.instructor = instructor;
        this.tipo = tipo;
        this.inicio = inicio;
        this.fin = fin;
        this.dia = dia;
        this.sucursal = sucursal;
    }

    public List<InscripcionModel> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<InscripcionModel> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public ClaseModel(Long id, Integer cupo, String instructor, Tipo tipo, LocalTime inicio, LocalTime fin,
            DayOfWeek dia, SucursalModel sucursal, List<InscripcionModel> inscripciones) {
        this.id = id;
        this.cupo = cupo;
        this.instructor = instructor;
        this.tipo = tipo;
        this.inicio = inicio;
        this.fin = fin;
        this.dia = dia;
        this.sucursal = sucursal;
        this.inscripciones = inscripciones;
    }

}
