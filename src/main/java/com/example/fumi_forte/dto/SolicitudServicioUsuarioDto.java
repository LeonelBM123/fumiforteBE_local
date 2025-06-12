package com.example.fumi_forte.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudServicioUsuarioDto {
    private Long idSolicitudServicio;
    private String nombre;
    private String tipoCliente;
    private String correo;
    private String telefono;
    private String estado;
    private String direccionEscrita;
    private String ubicacionGps;
}
