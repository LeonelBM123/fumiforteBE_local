package com.example.fumi_forte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class MontosPendientesDto {
    private BigDecimal montoPendienteCotizacion;
    private List<BigDecimal> montosPendientesSesion;
}
