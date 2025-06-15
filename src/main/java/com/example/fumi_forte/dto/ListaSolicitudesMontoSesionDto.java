package com.example.fumi_forte.dto;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
public class ListaSolicitudesMontoSesionDto {
    private Long idSolicitudServicio;
    private List<SesionMontoDto> sesiones;
}
