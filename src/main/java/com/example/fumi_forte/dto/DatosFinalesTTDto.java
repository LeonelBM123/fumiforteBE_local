package com.example.fumi_forte.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Data
public class DatosFinalesTTDto {
    private Long idSolicitudServicio;
    private Long idCliente;
    private BigDecimal montoPendienteCotizacion;
    private String requiereCertificado;
    private List<SesionDto> sesiones;
}
