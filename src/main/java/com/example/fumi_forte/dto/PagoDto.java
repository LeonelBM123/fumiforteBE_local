package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDto {
    private Long idPago;
    private LocalDate fecha;
    private BigDecimal monto;
    private String tipoPago;
    private String nroVoucher;
    private String estado;
    private Long idCliente;
}
