package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pago_sesion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_sesion")
    private Long idPagoSesion;

    @OneToOne
    @JoinColumn(name = "id_pago", nullable = false)
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "id_sesion", nullable = false)
    private Sesion sesion;
}
