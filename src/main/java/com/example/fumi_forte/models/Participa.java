package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participa {

    @EmbeddedId
    private ParticipaId id;

    @ManyToOne
    @MapsId("idTrabajador")  
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

    @ManyToOne
    @MapsId("idSesion")      
    @JoinColumn(name = "id_sesion", nullable = false)
    private Sesion sesion;

    @Column(name = "observaciones", columnDefinition = "text")
    private String observaciones;

    @Column(name = "pruebas", columnDefinition = "text")
    private String pruebas;

    // Constructor para crear Participa fácil, seteando id compuesto automáticamente
    public Participa(Sesion sesion, Trabajador trabajador) {
        this.sesion = sesion;
        this.trabajador = trabajador;
        this.id = new ParticipaId(trabajador.getIdTrabajador(), sesion.getIdSesion());
    }
}
