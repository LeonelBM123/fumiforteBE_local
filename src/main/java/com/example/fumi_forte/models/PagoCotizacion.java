package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pago_cotizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagoCotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_cotizacion")
    private Long idPagoCotizacion;
    
    @OneToOne
    @JoinColumn(name = "id_pago", nullable = false)
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "id_solicitud_servicio", nullable = false)
    private SolicitudServicio idSolicitudServicio;
    
    public PagoCotizacion(Pago pago, SolicitudServicio solicitudServicio) {
        this.pago = pago;
        this.idSolicitudServicio = solicitudServicio;
    }
}
