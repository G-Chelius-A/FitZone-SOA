package com.api.fitzone.models;

import java.time.LocalDate;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="socios")
public class SocioModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_socio", nullable=false)
    private Long id;

    @Column(name="nombre", length=100, nullable=false)
    @NotNull(message="Debe indicar su nombre")
    private String nombre;

    @Email(message="Debe ingresar un email válido")
    @NotNull(message="Debe ingresar un email")
    @Column(name="email", nullable=false)
    private String email;

    @Column(name="telefono", nullable=false)
    private Integer telefono;

    @Column(name="fecha_creacion", nullable=false, updatable=false)
    private LocalDateTime fechaCreacion;

    @Column(name="vigencia_membresia")
    private LocalDate vigenciaMembresia;

    @JsonIgnore 
    @OneToMany(mappedBy="socio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("socio")
    private List<InscripcionModel> inscripciones = new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.fechaCreacion = LocalDateTime.now();
        if(vigenciaMembresia==null){
            this.vigenciaMembresia = LocalDate.now().plusYears(1);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getVigenciaMembresia() {
        return vigenciaMembresia;
    }

    public void setVigenciaMembresia(LocalDate vigenciaMembresia) {
        this.vigenciaMembresia = vigenciaMembresia;
    }

    public List<InscripcionModel> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<InscripcionModel> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public SocioModel() {
    }

    public SocioModel(Long id, String nombre, @Email String email, Integer telefono, LocalDateTime fechaCreacion,
            LocalDate vigenciaMembresia, List<InscripcionModel> inscripciones) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.vigenciaMembresia = vigenciaMembresia;
        this.inscripciones = inscripciones;
    }

    public SocioModel(Long id, String nombre, @Email String email, LocalDateTime fechaCreacion,
            LocalDate vigenciaMembresia) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
        this.vigenciaMembresia = vigenciaMembresia;
    }

    
}
