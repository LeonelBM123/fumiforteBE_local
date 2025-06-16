package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministrarPagosDto {
    private Long idPago;
    private Long idCliente;
    private Long idSolicitudServicio;
    private Long idSesion;
    private LocalDate fecha;
    private String nombreCompleto;
    private String tipoCliente;
    private String tipoServicio;
    private String tipoPago;
    private String nroVoucher;
    private BigDecimal monto;
    private String estado;
}
