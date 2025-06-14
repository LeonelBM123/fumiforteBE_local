package com.example.fumi_forte.dto;

import lombok.*;

@Data
public class TrabajadorDto {
    private Long idTrabajador;
    private String nombreCompleto;
    private String especialidad;
    private String telefono;
    private String correo;
}
