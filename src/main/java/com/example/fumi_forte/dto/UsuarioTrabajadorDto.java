package com.example.fumi_forte.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.*;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioTrabajadorDto {
    // Datos Usuario
    private Long idUsuario;
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
    
    public UsuarioTrabajadorDto(Long idUsuario, String nombreCompleto, String telefono, String estado) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.estado = estado;
    }

}
