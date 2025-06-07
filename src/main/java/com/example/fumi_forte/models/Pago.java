
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

//hola mundo

public class Pago {
    @Id
    @Column(name = "id_pago")
    private Long idPago;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "monto", nullable = false, precision = 5, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "tipo_pago", nullable = false, length = 20)
    private String tipoPago;
    
    @Column(name = "nro_voucher", length = 50)
    private String nroVoucher;
    
    // Relación muchos a uno con Cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;
}


