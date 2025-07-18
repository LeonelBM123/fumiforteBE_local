package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import lombok.*;
        
@Data
@AllArgsConstructor
public class KPIsDto {
    private long serviciosCompletados;
    private long serviciosProgramados;
    private long solicitudesPendientes;
    private BigDecimal totalIngresos;
}
