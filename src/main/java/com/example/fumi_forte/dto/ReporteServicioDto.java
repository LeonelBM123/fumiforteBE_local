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
public class ReporteServicioDto {
    private int idSolicitudServicio;
    private int idSesion;
    private int nroSesion;
    private String trabajadores;
    private LocalDate fecha;
    private LocalTime hora;
    private String nombreCliente;
    private String direccionEscrita;
    private BigDecimal montoPendienteSesion;
    private String estadoPago;
}


