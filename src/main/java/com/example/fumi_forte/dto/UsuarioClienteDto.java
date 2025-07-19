package com.example.fumi_forte.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioClienteDto {
    // Datos Usuario
    private Long idUsuario;
    private String nombreCompleto;
    private String telefono;
    private String direccion;
    private String correo;
    private String contraseña;
    private String rol;
    private String estado;

    // Datos Cliente
    private String tipoCliente;
    private String razonSocial;
    private String nit;
    
    public UsuarioClienteDto(Long idUsuario, String nombreCompleto, String telefono, String estado, String tipoCliente) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.estado = estado;
        this.tipoCliente = tipoCliente;
    }
}
