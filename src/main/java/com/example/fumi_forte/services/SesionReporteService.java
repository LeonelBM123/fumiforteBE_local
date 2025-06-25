
package com.example.fumi_forte.services;

import com.example.fumi_forte.dto.SesionDto;
import com.example.fumi_forte.dto.SesionReporteDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SesionReporteService {
   @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SesionReporteDto> buscarSesiones(Long idSolicitud, String estado, String montoPendiente) {
        StringBuilder sql = new StringBuilder("""
            SELECT s.id_sesion, s.fecha, s.hora, s.monto_pendiente_sesion, s.estado, 
                   s.nro_sesion, s.id_solicitud_servicio
            FROM sesion s
            WHERE s.id_solicitud_servicio = ?
        """);

        List<Object> params = new ArrayList<>();
        params.add(idSolicitud);

        if (estado != null && !estado.isBlank()) {
            sql.append(" AND s.estado ILIKE ? ");
            params.add("%" + estado + "%");
        }

        if (montoPendiente != null && !montoPendiente.isBlank()) {
            switch (montoPendiente.toLowerCase()) {
                case "impaga" -> sql.append(" AND s.monto_pendiente_sesion > 0 ");
                case "pagado" -> sql.append(" AND s.monto_pendiente_sesion = 0 ");
            }
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new SesionReporteDto(
            rs.getLong("id_sesion"),
            rs.getObject("fecha", LocalDate.class),
            rs.getObject("hora", LocalTime.class),
            rs.getBigDecimal("monto_pendiente_sesion"),
            rs.getString("estado"),
            rs.getShort("nro_sesion"),
            rs.getLong("id_solicitud_servicio")
        ));
    } 
}
