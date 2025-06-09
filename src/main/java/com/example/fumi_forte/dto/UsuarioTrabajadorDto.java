package com.example.fumi_forte.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioTrabajadorDto {
    // Datos Usuario
    private String nombreCompleto;
    private String telefono;
    private String direccion;
    private String correo;
    private String contraseña;
    private String rol;
    private String estado;

    // Datos Trabajador
    private String especialidad;
    private LocalDate fechaInicio;
}
