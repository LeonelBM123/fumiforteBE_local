/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.UsuarioReporteDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsuarioReporteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UsuarioReporteDto> buscarUsuarios(String nombre, String estado, String rol) {
        String sql = """
            SELECT id_usuario, nombre_completo, correo, estado, rol, telefono, direccion
            FROM usuario
            WHERE nombre_completo like ? AND estado like ? AND rol like ?
            ORDER BY nombre_completo asc
        """;

        return jdbcTemplate.query(sql, new Object[]{nombre, estado, rol}, (rs, rowNum) -> {
            UsuarioReporteDto usuario = new UsuarioReporteDto();
            usuario.setIdUsuario(rs.getLong("id_usuario"));
            usuario.setNombreCompleto(rs.getString("nombre_completo"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setEstado(rs.getString("estado"));
            usuario.setRol(rs.getString("rol"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setDireccion(rs.getString("direccion"));
            return usuario;
        });
    }

}
