package com.example.fumi_forte.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private SolicitudServicio solicitud;
    
    @Column(nullable = false)
    private Integer puntuacion; // de 1 a 5
    
    @Column(length = 500)
    private String comentario;
    
    @Column(name = "fecha_calificacion")
    private LocalDateTime fechaCalificacion;
    
    // Constructor vacío (requerido por JPA)
    public Calificacion() {
        this.fechaCalificacion = LocalDateTime.now();
    }
    
    // Constructor con parámetros (opcional, pero útil)
    public Calificacion(SolicitudServicio solicitud, Integer puntuacion, String comentario) {
        this.solicitud = solicitud;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fechaCalificacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public SolicitudServicio getSolicitud() {
        return solicitud;
    }
    
    public void setSolicitud(SolicitudServicio solicitud) {
        this.solicitud = solicitud;
    }
    
    public Integer getPuntuacion() {
        return puntuacion;
    }
    
    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }
    
    public void setFechaCalificacion(LocalDateTime fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }
    
    // toString para debugging (opcional)
    @Override
    public String toString() {
        return "Calificacion{" +
                "id=" + id +
                ", solicitud=" + (solicitud != null ? solicitud.getIdSolicitudServicio() : null) +
                ", puntuacion=" + puntuacion +
                ", comentario='" + comentario + '\'' +
                ", fechaCalificacion=" + fechaCalificacion +
                '}';
    }
}