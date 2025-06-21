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
    private Integer sesion;
    private List<Long> trabajadores;
}
