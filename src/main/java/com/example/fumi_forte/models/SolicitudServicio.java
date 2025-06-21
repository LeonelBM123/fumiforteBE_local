package com.example.fumi_forte.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "solicitud_servicio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud_servicio")
    private Long idSolicitudServicio;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ubicacion_gps", nullable = false, length = 50)
    private String ubicacionGps;

    @Column(name = "direccion_escrita", nullable = false, columnDefinition = "TEXT")
    private String direccionEscrita;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado;

    @Column(name = "monto_pendiente_cotizacion", precision = 5, scale = 2)
    private BigDecimal montoPendienteCotizacion;

    @Column(name = "cantidad_sesiones", nullable = false)
    private Short cantidadSesiones = 0;

    @Column (name = "requiere_certificado", length = 2, nullable = false)
    private String requiereCertificado;
    
    @Column (name = "id_cliente")
    private Long idCliente;
    
    @Column (name = "id_gerente")
    private Long idGerente;
    
    @Column (name = "id_certificado")
    private Long idCertificado;

    public SolicitudServicio(Long idSolicitudServicio) {
        this.idSolicitudServicio = idSolicitudServicio;
    }
}   