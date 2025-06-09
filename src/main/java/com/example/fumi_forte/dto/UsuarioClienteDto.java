package com.example.fumi_forte.dto;

import lombok.Data;

@Data
public class UsuarioClienteDto {
    // Datos Usuario
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
}
