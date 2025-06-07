package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CotizacionDto {
    private Long idCotizacion;
    private LocalDate fecha;
    private BigDecimal costoCotizacion;
    private BigDecimal costoSesion;
    private Long idSolicitudServicio;
    private Long idGerente;
}
