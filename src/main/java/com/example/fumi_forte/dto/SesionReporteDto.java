package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SesionReporteDto {
    private Long idSesion;
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal montoPendienteSesion;
    private String estado;
    private Short nroSesion;
    private Long idSolicitudServicio;
}
