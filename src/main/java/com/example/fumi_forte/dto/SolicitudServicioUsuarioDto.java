package com.example.fumi_forte.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolicitudServicioUsuarioDto {
    private Long idSolicitudServicio;
    private Long idCliente;
    private String nombre;
    private String tipoCliente;
    private String correo;
    private String telefono;
    private String estado;
    private String direccionEscrita;
    private String ubicacionGps;
    
    public SolicitudServicioUsuarioDto(Long idSolicitudServicio, Long idCliente, String nombre) {
        this.idSolicitudServicio = idSolicitudServicio;
        this.idCliente = idCliente;
        this.nombre = nombre;
    }
}
