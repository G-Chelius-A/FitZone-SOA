package com.api.fitzone.models;

import java.time.LocalDateTime;

public class InscritoEvent {
    private Long idInscripcion;
    private Long idSocio;
    private Long idClase;
    private LocalDateTime fecha;
    private String correo;

    public Long getIdInscripcion() {
        return idInscripcion;
    }
    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }
    public Long getIdSocio() {
        return idSocio;
    }
    public void setIdSocio(Long idSocio) {
        this.idSocio = idSocio;
    }
    public Long getIdClase() {
        return idClase;
    }
    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public InscritoEvent(Long idInscripcion, Long idSocio, Long idClase, LocalDateTime fecha, String correo) {
        this.idInscripcion = idInscripcion;
        this.idSocio = idSocio;
        this.idClase = idClase;
        this.fecha = fecha;
        this.correo = correo;
    }
    

}
