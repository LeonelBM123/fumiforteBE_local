package com.example.fumi_forte.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@Entity
@Table(name = "sesion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long idSesion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "monto_pendiente_sesion", nullable = false, precision = 5, scale = 2)
    private BigDecimal montoPendienteSesion;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado;

    @Column(name = "nro_sesion", nullable = false)
    private Short nroSesion;

    @ManyToOne
    @JoinColumn(
        name = "id_solicitud_servicio", 
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_sesion_solicitud_servicio")
    )
    private SolicitudServicio solicitudServicio;
}
