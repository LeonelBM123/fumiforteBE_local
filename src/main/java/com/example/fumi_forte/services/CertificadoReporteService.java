/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.CertificadoReporteDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author PC
 */
@Service
public class CertificadoReporteService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CertificadoReporteDto> buscarPorEstado(String estado) {
        String estadoFiltrado = "%" + estado + "%";

        String sql = """
            SELECT id_certificado, fecha_emision, fecha_vencimiento, estado
            FROM certificado_fumigacion
            WHERE estado ILIKE ?
            order by fecha_vencimiento asc
        """;

        return jdbcTemplate.query(sql, new Object[]{estadoFiltrado}, (rs, rowNum) -> {
            return new CertificadoReporteDto(
                rs.getLong("id_certificado"),
                rs.getObject("fecha_emision", LocalDate.class),
                rs.getObject("fecha_vencimiento", LocalDate.class),
                rs.getString("estado")
            );
        });
    }
}