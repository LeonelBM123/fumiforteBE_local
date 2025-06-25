/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.BitacoraReporteDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BitacoraReporteService {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public List<BitacoraReporteDto> buscarPorAccion(LocalDateTime desde, LocalDateTime hasta) {
        String sql = """
            SELECT id_bitacora, accion, fecha_hora, ip, id_usuario
                    FROM bitacora
                    WHERE fecha_hora BETWEEN ? AND ?
                    ORDER BY fecha_hora DESC
        """;

        return jdbcTemplate.query(sql, new Object[]{desde, hasta}, (rs, rowNum) -> new BitacoraReporteDto(
                rs.getLong("id_bitacora"),
                rs.getString("accion"),
                rs.getObject("fecha_hora", LocalDateTime.class),
                rs.getString("ip"),
                rs.getLong("id_usuario")
        ));
    }
}
