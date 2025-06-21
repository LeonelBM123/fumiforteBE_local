package com.example.fumi_forte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class MontosPendientesDto {
    private Long idCliente;
    private Long idSolicitudServicio;
    private BigDecimal montoPendienteCotizacion;
    private List<BigDecimal> montosPendientesSesion;
    private List<String> estadosSesiones;
}
