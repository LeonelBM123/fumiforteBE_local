package com.example.fumi_forte.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "cotizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cotizacion {
    @Id
    @Column(name = "id_cotizacion")
    private Long idCotizacion; 
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "costo_cotizacion", nullable = false, precision = 5, scale = 2)
    private BigDecimal costoCotizacion;
    
    @Column(name = "costo_sesion", nullable = false, precision = 5, scale = 2)
    private BigDecimal costoSesion;
    
    // Relación uno a uno con SolicitudServicio
    @OneToOne
    @JoinColumn(name = "id_solicitud_servicio", referencedColumnName = "id_solicitud_servicio")
    private SolicitudServicio solicitudServicio;

    // Relación muchos a uno con Gerente
    @ManyToOne
    @JoinColumn(name = "id_gerente", referencedColumnName = "id_gerente")
    private Gerente gerente;
}