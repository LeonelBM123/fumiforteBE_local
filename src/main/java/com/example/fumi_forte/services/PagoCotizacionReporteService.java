/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.PagoSesionReporteDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author PC
 */
public class PagoCotizacionReporteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PagoSesionReporteDto> buscarPagosPorFiltros2(LocalDate fecha, String tipoPago) {
        String sql = """
            SELECT p.id_pago, p.fecha, p.monto, p.tipo_pago, p.nro_voucher,
                   p.id_cliente, p.estado, ps.id_pago_sesion, ps.id_sesion
            FROM pago p
            JOIN pago_cotizacion ps ON p.id_pago = ps.id_pago
            WHERE
                (? IS NULL OR p.fecha = ?) AND
                (? IS NULL OR p.tipo_pago ILIKE ?)
        """;

        String tipoPagoLike = (tipoPago != null) ? "%" + tipoPago + "%" : null;

        return jdbcTemplate.query(
            sql,
            new Object[]{
                fecha, fecha,
                tipoPago, tipoPagoLike
            },
            (rs, rowNum) -> new PagoSesionReporteDto(
                rs.getLong("id_pago"),
                rs.getObject("fecha", LocalDate.class),
                rs.getBigDecimal("monto"),
                rs.getString("tipo_pago"),
                rs.getString("nro_voucher"),
                rs.getLong("id_cliente"),
                rs.getString("estado"),
                rs.getLong("id_pago_sesion"),
                rs.getLong("id_sesion")
            )
        );
    }
}
