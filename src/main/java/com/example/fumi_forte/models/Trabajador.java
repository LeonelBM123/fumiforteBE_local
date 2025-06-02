package com.example.fumi_forte.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "trabajador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabajador {
    @Id
    @Column(name = "id_trabajador")
    private Long idTrabajador; 

    @Column(name = "especialidad", nullable = false, length = 50)
    private String especialidad;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @OneToOne
    @MapsId 
    @JoinColumn(name = "id_trabajador") 
    private Usuario usuario;
}
