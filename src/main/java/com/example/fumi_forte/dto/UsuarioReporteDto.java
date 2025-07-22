package com.example.fumi_forte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioReporteDto {
    private Long idUsuario;
    private String nombreCompleto;
    private String correo;
    private String estado;
    private String rol;
    private String telefono;
    private String direccion;
}
