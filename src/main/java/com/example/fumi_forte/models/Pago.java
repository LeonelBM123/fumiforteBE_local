
package com.example.fumi_forte.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;
    
    @Column(name = "fecha")
    private LocalDate fecha;
    
    @Column(name = "monto", nullable = false, precision = 5, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "tipo_pago", length = 20)
    private String tipoPago;
    
    @Column(name = "nro_voucher", length = 50)
    private String nroVoucher;
    
    @Column(name = "estado", length = 20)
    private String estado;
    
    // Relación muchos a uno con Cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;
}


