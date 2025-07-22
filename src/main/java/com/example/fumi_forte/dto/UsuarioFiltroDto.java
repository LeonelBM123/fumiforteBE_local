package com.example.fumi_forte.dto;

import lombok.Data;

@Data
public class UsuarioFiltroDto {
    private Long idUsuario;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;
    private String rol;
    private String tipoCliente;
    private String estado;
    
    public UsuarioFiltroDto(Long idUsuario, String nombre, String telefono, String direccion,
                            String correo, String rol, String tipoCliente, String estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.rol = rol;
        this.tipoCliente = tipoCliente;
        this.estado = estado;
    }
    
    public UsuarioFiltroDto() {}
}
