package com.api.fitzone.models;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "sucursales")
public class SucursalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal", nullable = false)
    public Long id;
    @Column(name = "direccion", nullable = false)
    public String direccion;
    @Column(name = "ciudad", nullable = false)
    public String ciudad;
    @Column(name = "estado", nullable = false)
    public String estado;

    @JsonIgnore
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("sucursal")
    public List<ClaseModel> clases = new ArrayList<>();

    // Getters, setters y constructores.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ClaseModel> getClases() {
        return clases;
    }

    public void setClases(List<ClaseModel> clases) {
        this.clases = clases;
    }

    public SucursalModel() {
    }

    public SucursalModel(Long id, String direccion, String ciudad, String estado, List<ClaseModel> clases) {
        this.id = id;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.clases = clases;
    }

    public SucursalModel(Long id, String direccion, String ciudad, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
    }

}
