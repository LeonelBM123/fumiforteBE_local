/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.SolicitudReporteDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SolicitudReporteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
 

    public List<SolicitudReporteDto> buscarSolicitud(String estado, String requiere_certificado) {
        StringBuilder sql = new StringBuilder("""
            SELECT id_solicitud_servicio, descripcion, ubicacion_gps, direccion_escrita,
                   estado, monto_pendiente_cotizacion, cantidad_sesiones,
                   requiere_certificado, id_cliente, id_gerente, id_certificado
            FROM solicitud_servicio
            WHERE 1 = 1
        """);

        List<Object> params = new ArrayList<>();

        if (estado != null && !estado.isBlank()) {
            sql.append(" AND estado ILIKE ? ");
            params.add("%" + estado + "%");
        }

        if (requiere_certificado != null && !requiere_certificado.isBlank()) {
            sql.append(" AND requiere_certificado ILIKE ? ");
            params.add("%" + requiere_certificado + "%");
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new SolicitudReporteDto(
                rs.getLong("id_solicitud_servicio"),
                rs.getString("descripcion"),
                rs.getString("ubicacion_gps"),
                rs.getString("direccion_escrita"),
                rs.getString("estado"),
                rs.getBigDecimal("monto_pendiente_cotizacion"),
                rs.getShort("cantidad_sesiones"),
                rs.getString("requiere_certificado"),
                rs.getLong("id_cliente"),
                rs.getLong("id_gerente"),
                rs.getLong("id_certificado")
        ));
    }
}