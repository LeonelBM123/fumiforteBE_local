package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.*;

@Data
public class SesionDto {
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal monto;
    private Short sesion;
    private List<Long> trabajadores;
    private String estado;
    
    // Constructor sin la lista para usar con JPQL
    public SesionDto(LocalDate fecha, LocalTime hora, BigDecimal monto, Short sesion, String estado) {
        this.fecha = fecha;
        this.hora = hora;
        this.monto = monto;
        this.sesion = sesion;
        this.estado = estado;
    }
}
